package ir.app;

import ir.document.Document;
import ir.io.DocumentReader;
import ir.preprocessing.TextPreprocessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IRApplication {
    // method untuk mengatur alur utama program
    public void run() {
        //insialisasi kelas DocumentReader
        DocumentReader reader = new DocumentReader();
        //buat list document untuk menaruh kalimat-kalimat yang ada di dokumen
        List<Document> documents = reader.readDocuments("data/documents.txt");
        // buat TextPreprocessor
        TextPreprocessor preprocessor = new TextPreprocessor();
        // STEP 3:
        // Bangun InvertedIndex menggunakan Indexer.
        //
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

    private void buildIndex() {
        // Method ini bertugas membangun index.
        //
        // STEP 1:
        // Panggil DocumentReader untuk membaca documents.txt.
        //
        // STEP 2:
        // Hasil pembacaan berupa List<Document>.
        //
        // STEP 3:
        // Buat object TextPreprocessor.
        //
        // STEP 4:
        // Buat object Indexer.
        //
        // STEP 5:
        // Panggil Indexer.buildIndex(documents).
        //
        // STEP 6:
        // Simpan hasil InvertedIndex untuk dipakai SearchEngine.
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