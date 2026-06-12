package ir.app;

import ir.document.Document;
import ir.evaluation.EvaluationResult;
import ir.evaluation.Evaluator;
import ir.evaluation.RelevanceJudgment;
import ir.index.IndexStatistics;
import ir.index.Indexer;
import ir.index.InvertedIndex;
import ir.io.ConsoleInput;
import ir.io.DocumentReader;
import ir.io.QrelsReader;
import ir.io.QueryReader;
import ir.preprocessing.TextPreprocessor;
import ir.query.Query;
import ir.retrieval.ModelType;
import ir.retrieval.RetrievalModel;
import ir.retrieval.RetrievalModelFactory;
import ir.retrieval.SearchEngine;
import ir.retrieval.SearchResult;
import java.util.ArrayList;
import java.util.List;

public class IRApplication {
    // method untuk mengatur alur utama program
    public void run() {
        //bangun inverted index dari documents.txt
        InvertedIndex index = buildIndex();
        //panggil index statistic buat menampilkan ringkasan index
        IndexStatistics statistics = new IndexStatistics();
        statistics.printSummary(index);
        //jalankan demo ranking untuk query dari queries.txt
        runDemoMode(index);
        //jalankan evaluasi otomatis menggunakan qrels.txt
        runEvaluationMode(index);
    }

    //method ini berfungsi untuk membangun index
    private InvertedIndex buildIndex() {
        //panggil documentReader untuk membaca documents.txt
        DocumentReader reader = new DocumentReader();
        //hasil pembacaan berupa List<Document>
        List<Document> documents = reader.readDocuments("data/documents.txt");
        //buat object text preprocessor
        TextPreprocessor preprocessor = new TextPreprocessor();
        //buat object Indexer
        Indexer indexer = new Indexer(preprocessor);
        //panggil Indexer.buildIndex(documents)
        InvertedIndex index = indexer.buildIndex(documents);
        //kembalikan hasil inverted index untuk dipakai search engine
        return index;
    }

    private void runInteractiveMode(InvertedIndex index) {
        //buat ConsoleInput untuk membaca input terminal
        ConsoleInput input = new ConsoleInput();
        //baca query yang diketik user
        String query = input.readQuery();
        //baca pilihan model dari user
        String modelChoice = input.readModelChoice();
        //baca jumlah hasil teratas yang ingin ditampilkan
        int topK = input.readTopK();
        //buat object TextPreprocessor
        TextPreprocessor preprocessor = new TextPreprocessor();
        //buat object RetrievalModelFactory
        RetrievalModelFactory factory = new RetrievalModelFactory();
        //ubah pilihan user menjadi ModelType
        ModelType modelType = parseModelChoice(modelChoice);
        //buat model retrieval berdasarkan pilihan user
        RetrievalModel model = factory.create(modelType, preprocessor);
        //buat object SearchEngine
        SearchEngine engine = new SearchEngine(index);
        //jalankan search menggunakan query, model, dan topK
        List<SearchResult> results = engine.search(query, model, topK);
        //tampilkan nama model yang dipakai
        System.out.println("===== " + model.getModelName() + " Results =====");
        //loop semua hasil ranking
        for (SearchResult result : results) {
            //print documentId dan score ke terminal
            System.out.println(result.getDocumentId() + " : " + result.getScore());
        }
    }

    private void runEvaluationMode(InvertedIndex index) {
        //buat QueryReader untuk membaca queries.txt
        QueryReader queryReader = new QueryReader();
        //baca queries.txt menjadi List<Query>
        List<Query> queries = queryReader.readQueries("data/queries.txt");
        //buat QrelsReader untuk membaca qrels.txt
        QrelsReader qrelsReader = new QrelsReader();
        //baca qrels.txt menjadi List<RelevanceJudgment>
        List<RelevanceJudgment> judgments = qrelsReader.readQrels("data/qrels.txt");
        //buat object TextPreprocessor
        TextPreprocessor preprocessor = new TextPreprocessor();
        //buat object RetrievalModelFactory
        RetrievalModelFactory factory = new RetrievalModelFactory();
        //buat object SearchEngine
        SearchEngine engine = new SearchEngine(index);
        //buat object Evaluator
        Evaluator evaluator = new Evaluator();
        //inisialisasi list untuk menyimpan hasil evaluasi
        List<EvaluationResult> evaluationResults = new ArrayList<EvaluationResult>();
        //tentukan topK untuk evaluasi
        int topK = 10;
        //ambil semua model sesuai deskripsi tugas
        ModelType[] modelTypes = getAllModelTypes();
        //loop setiap model yang akan dievaluasi
        for (ModelType modelType : modelTypes) {
            //inisialisasi list untuk menyimpan hasil evaluasi model saat ini
            List<EvaluationResult> modelResults = new ArrayList<EvaluationResult>();
            //inisialisasi nama model
            String modelName = modelType.toString();
            try {
                //buat model retrieval dari factory
                RetrievalModel model = factory.create(modelType, preprocessor);
                //ambil nama model
                modelName = model.getModelName();
                //loop setiap query dari queries.txt
                for (Query query : queries) {
                    //jalankan search untuk query saat ini
                    List<SearchResult> rankedResults = engine.search(query.getText(), model, topK);
                    //hitung evaluasi untuk model dan query saat ini
                    EvaluationResult result = evaluator.evaluate(
                            model.getModelName(),
                            query.getId(),
                            rankedResults,
                            judgments,
                            topK
                    );

                    //masukkan hasil evaluasi ke list model saat ini
                    modelResults.add(result);
                    //masukkan hasil evaluasi ke list keseluruhan
                    evaluationResults.add(result);

                    //print hasil evaluasi untuk query saat ini
                    printEvaluationResult(result);
                }
            } catch (RuntimeException e) {
                //kalau model belum selesai maka lanjut model berikutnya
                System.out.println("Model " + modelName + " belum bisa dievaluasi");
            }

            //hitung MAP untuk model saat ini
            double map = evaluator.computeMeanAveragePrecision(modelResults);
            //tampilkan MAP untuk perbandingan model
            System.out.println("MAP " + modelName + " : " + map);
        }
    }

    private ModelType parseModelChoice(String modelChoice) {
        //kalau user memilih 1 maka gunakan BIM
        if (modelChoice.equals("1")) {
            return ModelType.BIM;
        }
        //kalau user memilih 2 maka gunakan Two-Poisson
        if (modelChoice.equals("2")) {
            return ModelType.TWO_POISSON;
        }
        //kalau user memilih 3 maka gunakan BM25
        if (modelChoice.equals("3")) {
            return ModelType.BM25;
        }
        //kalau user memilih 4 maka gunakan BM10
        if (modelChoice.equals("4")) {
            return ModelType.BM10;
        }
        //kalau input tidak dikenal maka default BM25
        return ModelType.BM25;
    }

    private void runDemoMode(InvertedIndex index) {
        //buat QueryReader untuk membaca queries.txt
        QueryReader queryReader = new QueryReader();
        //baca semua query dari file
        List<Query> queries = queryReader.readQueries("data/queries.txt");
        //kalau query kosong maka demo tidak dijalankan
        if (queries.isEmpty()) {
            return;
        }

        //ambil query pertama sebagai contoh demo
        Query query = queries.get(0);
        //buat object TextPreprocessor
        TextPreprocessor preprocessor = new TextPreprocessor();
        //buat object RetrievalModelFactory
        RetrievalModelFactory factory = new RetrievalModelFactory();
        //buat object SearchEngine
        SearchEngine engine = new SearchEngine(index);
        //ambil semua model sesuai deskripsi tugas
        ModelType[] modelTypes = getAllModelTypes();
        //tentukan jumlah ranking yang ditampilkan
        int topK = 5;

        //tampilkan query yang dipakai untuk demo
        System.out.println("===== Demo Query: " + query.getId() + " =====");
        System.out.println(query.getText());

        //loop semua model retrieval
        for (ModelType modelType : modelTypes) {
            try {
                //buat model dari factory
                RetrievalModel model = factory.create(modelType, preprocessor);
                //jalankan search untuk query demo
                List<SearchResult> results = engine.search(query.getText(), model, topK);
                //tampilkan nama model
                System.out.println("===== " + model.getModelName() + " Results =====");
                //loop semua hasil ranking
                for (SearchResult result : results) {
                    //print documentId dan score ke terminal
                    System.out.println(result.getDocumentId() + " : " + result.getScore());
                }
            } catch (RuntimeException e) {
                //kalau model belum selesai maka lanjut model berikutnya
                System.out.println("Model " + modelType + " belum bisa dijalankan");
            }
        }
    }

    private ModelType[] getAllModelTypes() {
        //return semua model yang diminta di deskripsi tugas
        return new ModelType[]{
                ModelType.BIM,
                ModelType.TWO_POISSON,
                ModelType.BM25,
                ModelType.BM10
        };
    }

    private void printEvaluationResult(EvaluationResult result) {
        //print hasil evaluasi agar mudah dipakai untuk laporan
        System.out.println(
                result.getModelName()
                        + " | " + result.getQueryId()
                        + " | P@" + result.getTopK() + " = " + result.getPrecisionAtK()
                        + " | R@" + result.getTopK() + " = " + result.getRecallAtK()
                        + " | F1 = " + result.getF1AtK()
                        + " | AP = " + result.getAveragePrecision()
        );
    }
}
