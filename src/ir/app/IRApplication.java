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
import ir.io.ResultWriter;
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
        //buat ConsoleInput untuk memilih mode program
        ConsoleInput input = new ConsoleInput();
        //baca pilihan mode dari user
        String mode = input.readRunMode();
        //kalau pilih 1 maka jalankan input query manual dari terminal
        if(mode.equals("1")){
            runInteractiveMode(index, input);
            return;
        }
        //kalau pilih 2 maka jalankan query dari file queries.txt
        if(mode.equals("2")){
            runEvaluationMode(index, input);
            return;
        }
        //kalau pilihan tidak dikenal maka default memakai file queries.txt
        System.out.println("Pilihan tidak dikenal, memakai mode file queries.txt");
        runEvaluationMode(index, input);
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

    private void runInteractiveMode(InvertedIndex index, ConsoleInput input) {
        //minta user mengetik query
        String query = input.readQuery();
        //minta user memilih model retrieval
        String modelChoice = input.readModelChoice();
        //minta user memasukkan jumlah hasil yang ingin ditampilkan
        int topK = input.readTopK();
        //buat text preprocessor
        TextPreprocessor preprocessor = new TextPreprocessor();
        //buat model menggunakan RetrievalModelFactory
        RetrievalModelFactory models = new RetrievalModelFactory();
        //ubah pilihan user menjadi ModelType
        ModelType modelType = parseModelChoice(modelChoice);
        //buat model retrieval dari factory
        RetrievalModel model = createModel(models, modelType, preprocessor, input);
        //buat search engine memakai index
        SearchEngine engine = new SearchEngine(index);
        //jalankan SearchEngine.search()
        List<SearchResult> results = engine.search(query, model, topK);
        //print ranking hasil pencarian ke terminal
        ResultWriter writer = new ResultWriter();
        writer.printRanking(model.getModelName(), results);
    }

    private void runEvaluationMode(InvertedIndex index, ConsoleInput input) {
        QueryReader queryReader = new QueryReader();
        List<Query> queries = queryReader.readQueries("data/queries.txt");

        QrelsReader qrelsReader = new QrelsReader();
        List<RelevanceJudgment> judgments = qrelsReader.readQrels("data/qrels.txt");

        TextPreprocessor preprocessor = new TextPreprocessor();
        RetrievalModelFactory factory = new RetrievalModelFactory();
        SearchEngine engine = new SearchEngine(index);
        Evaluator evaluator = new Evaluator();

        // Beberapa nilai K untuk Precision@K.
        int[] kValues = {5, 10, 20};

        ModelType[] modelTypes = getAllModelTypes();

        System.out.println("===== Evaluation Summary =====");

        for (ModelType modelType : modelTypes) {
            List<EvaluationResult> modelResults = new ArrayList<EvaluationResult>();

            String modelName = modelType.toString();

            double totalPrecision = 0.0;
            double totalRecall = 0.0;
            double totalElevenPoint = 0.0;

            double[] totalPrecisionAtK = new double[kValues.length];

            try {
                RetrievalModel model = createModel(factory, modelType, preprocessor, input);
                modelName = model.getModelName();

                for (Query query : queries) {
                    // Ambil full ranking agar AP dan 11-point average lebih benar.
                    List<SearchResult> rankedResults = engine.search(query.getText(), model, 0);

                    // Evaluasi precision dan recall pada seluruh ranking.
                    EvaluationResult fullResult = evaluator.evaluate(
                            model.getModelName(),
                            query.getId(),
                            rankedResults,
                            judgments,
                            0
                    );

                    modelResults.add(fullResult);

                    totalPrecision += fullResult.getPrecisionAtK();
                    totalRecall += fullResult.getRecallAtK();

                    // Hitung 11-point average.
                    double elevenPointAverage = evaluator.computeElevenPointAverage(
                            query.getId(),
                            rankedResults,
                            judgments
                    );

                    totalElevenPoint += elevenPointAverage;

                    // Print hasil query.
                    System.out.println(
                            model.getModelName()
                                    + " | " + query.getId()
                                    + " | Precision = " + fullResult.getPrecisionAtK()
                                    + " | Recall = " + fullResult.getRecallAtK()
                                    + " | AP = " + fullResult.getAveragePrecision()
                                    + " | 11-Point = " + elevenPointAverage
                    );

                    // Hitung Precision@K untuk beberapa nilai K.
                    for (int i = 0; i < kValues.length; i++) {
                        EvaluationResult pkResult = evaluator.evaluate(
                                model.getModelName(),
                                query.getId(),
                                rankedResults,
                                judgments,
                                kValues[i]
                        );

                        totalPrecisionAtK[i] += pkResult.getPrecisionAtK();

                        System.out.println(
                                model.getModelName()
                                        + " | " + query.getId()
                                        + " | P@" + kValues[i]
                                        + " = " + pkResult.getPrecisionAtK()
                        );
                    }
                }
            } catch (RuntimeException e) {
                System.out.println("Model " + modelName + " belum bisa dievaluasi");
            }

            int totalQueries = queries.size();

            if (totalQueries > 0) {
                double avgPrecision = totalPrecision / totalQueries;
                double avgRecall = totalRecall / totalQueries;
                double map = evaluator.computeMeanAveragePrecision(modelResults);
                double avgElevenPoint = totalElevenPoint / totalQueries;

                System.out.println("----- Rata-rata Model: " + modelName + " -----");
                System.out.println("Precision = " + avgPrecision);
                System.out.println("Recall = " + avgRecall);

                for (int i = 0; i < kValues.length; i++) {
                    double avgPAtK = totalPrecisionAtK[i] / totalQueries;
                    System.out.println("P@" + kValues[i] + " = " + avgPAtK);
                }

                System.out.println("MAP = " + map);
                System.out.println("11-Point Average = " + avgElevenPoint);
                System.out.println("--------------------------------------");
            }
        }
    }

    private ModelType[] getAllModelTypes(){
        return new ModelType[]{ModelType.BIM, ModelType.TWO_POISSON, ModelType.BM25, ModelType.BM10};
    }

    private RetrievalModel createModel(RetrievalModelFactory models, ModelType modelType, TextPreprocessor preprocessor, ConsoleInput input){
        //kalau model yang dipilih adalah Two-Poisson maka minta parameter k
        if(modelType == ModelType.TWO_POISSON){
            //baca nilai k dari user, jika kosong maka pakai default 1.0
            double k = input.readDoubleParameter("k Two-Poisson", 1.0);
            //buat model Two-Poisson dengan parameter dari user
            return models.createTwoPoisson(preprocessor, k);
        }
        //kalau model yang dipilih adalah BM25 maka minta parameter k1 dan b
        if(modelType == ModelType.BM25){
            //baca nilai k1 dari user, jika kosong maka pakai default 1.5
            double k1 = input.readDoubleParameter("k1 BM25", 1.5);
            //baca nilai b dari user, jika kosong maka pakai default 0.75
            double b = input.readDoubleParameter("b BM25", 0.75);
            //buat model BM25 dengan parameter dari user
            return models.createBM25(preprocessor, k1, b);
        }
        //kalau bukan BM25 maka buat model seperti biasa
        return models.create(modelType, preprocessor);
    }

    private ModelType parseModelChoice(String modelChoice){
        //kalau user memilih "1" maka buatlah model BIM
        if(modelChoice.equals("1")){
            return ModelType.BIM;
        }
        //kalau user memilih "2" maka buatlah model Two-Poisson
        if(modelChoice.equals("2")){
            return ModelType.TWO_POISSON;
        }
        //kalau user memilih "3" maka buatlah model BM25
        if(modelChoice.equals("3")){
            return ModelType.BM25;
        }
        //kalau user memilih "1" maka buatlah model BIM
        if(modelChoice.equals("4")){
            return ModelType.BM10;
        }
        //kalau tidak memilih pakai model default yaitu BM25
        return ModelType.BM25;


    }
}
