package ir.app;

import ir.document.Document;
import ir.index.IndexStatistics;
import ir.index.Indexer;
import ir.index.InvertedIndex;
import ir.io.DocumentReader;
import ir.preprocessing.TextPreprocessor;
import java.util.List;

public class IRApplication {
    // method untuk mengatur alur utama program
    public void run() {
        //bangun inverted index dari documents.txt
        InvertedIndex index = buildIndex();
        //panggil index statistic buat menampilkan ringkasan index
        IndexStatistics statistics = new IndexStatistics();
        statistics.printSummary(index);
        // STEP 4:
        // Pilih mode program:
        // - interactive search: query diketik langsung dari terminal
        // - evaluation mode: query dibaca dari queries.txt
        //
        // STEP 5:
        // Jalankan model retrieval:
        // - BIM
        // - Two-Poisson
        // - BM25
        // - BM10
        //
        // STEP 6:
        // Tampilkan hasil ranking.
        //
        // STEP 7:
        // Jika evaluation mode, hitung Precision@K, Recall@K, dan MAP.
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

    private void runInteractiveMode() {
        // Method ini dipakai untuk demo terminal.
        //
        // STEP 1:
        // Minta user mengetik query.
        //
        // STEP 2:
        // Minta user memilih model retrieval.
        //
        // STEP 3:
        // Buat model menggunakan RetrievalModelFactory.
        //
        // STEP 4:
        // Jalankan SearchEngine.search().
        //
        // STEP 5:
        // Print ranking hasil pencarian ke terminal.
    }

    private void runEvaluationMode() {
        // Method ini dipakai untuk evaluasi otomatis.
        //
        // STEP 1:
        // Baca queries.txt menggunakan QueryReader.
        //
        // STEP 2:
        // Baca qrels.txt menggunakan QrelsReader.
        //
        // STEP 3:
        // Jalankan semua query pada semua model.
        //
        // STEP 4:
        // Hitung evaluasi setiap model.
        //
        // STEP 5:
        // Simpan hasil evaluasi menggunakan ResultWriter.
    }
}