package ir.retrieval.model;

import ir.index.InvertedIndex;
import ir.preprocessing.TextPreprocessor;
import ir.retrieval.RetrievalModel;
import ir.retrieval.SearchResult;

import java.util.List;

public class BM25Model implements RetrievalModel {

    private TextPreprocessor preprocessor;
    private double k1;
    private double b;

    public BM25Model(TextPreprocessor preprocessor) {
        // Menyimpan TextPreprocessor.
        // Set parameter BM25:
        // k1 = 1.5
        // b  = 0.75
    }

    @Override
    public String getModelName() {
        // Mengembalikan nama model.
        return "BM25";
    }

    @Override
    public List<SearchResult> rank(String query, InvertedIndex index) {
        // Menghitung ranking menggunakan BM25.
        //
        // STEP 1:
        // Preprocess query.
        //
        // STEP 2:
        // Loop semua dokumen.
        //
        // STEP 3:
        // Untuk setiap term query:
        // - ambil tf
        // - ambil df
        // - ambil total dokumen
        // - ambil document length
        // - ambil average document length
        //
        // STEP 4:
        // Hitung skor term BM25.
        //
        // STEP 5:
        // Jumlahkan semua skor term.
        //
        // STEP 6:
        // Buat SearchResult.
        //
        // STEP 7:
        // Return ranking.
        return null;
    }

    private double computeTermScore(
            int tf,
            int df,
            int totalDocuments,
            int documentLength,
            double averageDocumentLength
    ) {
        // Menghitung skor satu term pada BM25.
        //
        // Formula:
        // idf * ((tf * (k1 + 1)) /
        //       (tf + k1 * (1 - b + b * dl / avgdl)))
        //
        // STEP 1:
        // Jika tf = 0, return 0.
        //
        // STEP 2:
        // Hitung idf.
        //
        // STEP 3:
        // Hitung normalisasi panjang dokumen.
        //
        // STEP 4:
        // Hitung numerator.
        //
        // STEP 5:
        // Hitung denominator.
        //
        // STEP 6:
        // Return skor term.
        return 0.0;
    }

    private double computeIdf(int df, int totalDocuments) {
        // Menghitung IDF untuk BM25.
        //
        // Formula umum:
        // idf = log((N - df + 0.5) / (df + 0.5) + 1)
        return 0.0;
    }
}