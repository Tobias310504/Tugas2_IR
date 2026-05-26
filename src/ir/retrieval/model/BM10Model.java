package ir.retrieval.model;

import ir.index.InvertedIndex;
import ir.preprocessing.TextPreprocessor;
import ir.retrieval.RetrievalModel;
import ir.retrieval.SearchResult;

import java.util.List;

public class BM10Model implements RetrievalModel {

    private TextPreprocessor preprocessor;
    private double k1;

    public BM10Model(TextPreprocessor preprocessor) {
        // Menyimpan TextPreprocessor.
        // Set parameter k1, misalnya 1.5.
    }

    @Override
    public String getModelName() {
        // Mengembalikan nama model.
        return "BM10";
    }

    @Override
    public List<SearchResult> rank(String query, InvertedIndex index) {
        // Menghitung ranking menggunakan BM10.
        //
        // BM10 mirip BM25, tetapi tidak menggunakan
        // normalisasi panjang dokumen.
        //
        // STEP 1:
        // Preprocess query.
        //
        // STEP 2:
        // Loop semua dokumen.
        //
        // STEP 3:
        // Untuk setiap query term:
        // - ambil tf
        // - ambil df
        // - ambil total dokumen
        //
        // STEP 4:
        // Hitung skor term BM10.
        //
        // STEP 5:
        // Jumlahkan skor.
        //
        // STEP 6:
        // Buat SearchResult.
        //
        // STEP 7:
        // Return ranking.
        return null;
    }

    private double computeTermScore(int tf, int df, int totalDocuments) {
        // Menghitung skor satu term pada BM10.
        //
        // Formula sederhana:
        // idf * ((tf * (k1 + 1)) / (tf + k1))
        //
        // STEP 1:
        // Jika tf = 0, return 0.
        //
        // STEP 2:
        // Hitung idf.
        //
        // STEP 3:
        // Hitung numerator = tf * (k1 + 1).
        //
        // STEP 4:
        // Hitung denominator = tf + k1.
        //
        // STEP 5:
        // Return skor.
        return 0.0;
    }

    private double computeIdf(int df, int totalDocuments) {
        // Menghitung IDF untuk BM10.
        return 0.0;
    }
}