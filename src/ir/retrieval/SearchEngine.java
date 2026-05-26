package ir.retrieval;

import ir.index.InvertedIndex;
import java.util.List;

public class SearchEngine {

    private InvertedIndex index;

    public SearchEngine(InvertedIndex index) {
        // Menyimpan InvertedIndex yang sudah dibangun.
    }

    public List<SearchResult> search(String query, RetrievalModel model, int topK) {
        // Menjalankan pencarian dengan model tertentu.
        //
        // STEP 1:
        // Panggil model.rank(query, index).
        //
        // STEP 2:
        // Urutkan hasil berdasarkan skor descending.
        //
        // STEP 3:
        // Ambil topK dokumen.
        //
        // STEP 4:
        // Return hasil topK.
        return null;
    }
}