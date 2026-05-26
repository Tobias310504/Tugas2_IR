package ir.retrieval.model;

import ir.index.InvertedIndex;
import ir.preprocessing.TextPreprocessor;
import ir.retrieval.RetrievalModel;
import ir.retrieval.SearchResult;

import java.util.List;

public class TwoPoissonModel implements RetrievalModel {

    private TextPreprocessor preprocessor;
    private double k;

    public TwoPoissonModel(TextPreprocessor preprocessor) {
        // Menyimpan TextPreprocessor.
        // Set nilai k, misalnya 1.5.
    }

    @Override
    public String getModelName() {
        // Mengembalikan nama model.
        return "Two-Poisson";
    }

    @Override
    public List<SearchResult> rank(String query, InvertedIndex index) {
        // Menghitung ranking menggunakan Two-Poisson Model.
        //
        // STEP 1:
        // Preprocess query.
        //
        // STEP 2:
        // Loop semua dokumen.
        //
        // STEP 3:
        // Untuk setiap query term, ambil tf pada dokumen.
        //
        // STEP 4:
        // Hitung bobot term.
        //
        // STEP 5:
        // Hitung term score:
        // score_t = (tf * (k + 1) * weight) / (tf + k)
        //
        // STEP 6:
        // Jumlahkan semua term score menjadi skor dokumen.
        //
        // STEP 7:
        // Buat SearchResult.
        //
        // STEP 8:
        // Return ranking.
        return null;
    }

    private double computeTermScore(int tf, double weight) {
        // Menghitung skor satu term pada Two-Poisson.
        //
        // STEP 1:
        // Jika tf = 0, return 0.
        //
        // STEP 2:
        // Hitung numerator = tf * (k + 1) * weight.
        //
        // STEP 3:
        // Hitung denominator = tf + k.
        //
        // STEP 4:
        // Return numerator / denominator.
        return 0.0;
    }

    private double computeWeight(String term, InvertedIndex index) {
        // Menghitung bobot term.
        //
        // Untuk versi awal, boleh memakai bobot seperti BIM:
        // wt = log(0.5 * N / Nt)
        //
        // N  = total dokumen
        // Nt = document frequency term
        return 0.0;
    }
}