package ir.retrieval.model;

import ir.index.InvertedIndex;
import ir.preprocessing.TextPreprocessor;
import ir.retrieval.RetrievalModel;
import ir.retrieval.SearchResult;

import java.util.List;

public class BIMModel implements RetrievalModel {

    private TextPreprocessor preprocessor;

    public BIMModel(TextPreprocessor preprocessor) {
        // Menyimpan TextPreprocessor untuk memproses query.
    }

    @Override
    public String getModelName() {
        // Mengembalikan nama model.
        return "BIM";
    }

    @Override
    public List<SearchResult> rank(String query, InvertedIndex index) {
        // Menghitung ranking menggunakan Binary Independence Model.
        //
        // STEP 1:
        // Preprocess query.
        //
        // STEP 2:
        // Ambil term unik dari query.
        //
        // STEP 3:
        // Loop semua dokumen.
        //
        // STEP 4:
        // Untuk setiap dokumen, cek apakah term query muncul.
        //
        // STEP 5:
        // Jika term muncul, tambahkan bobot BIM.
        //
        // STEP 6:
        // Karena BIM binary, frekuensi term tidak dihitung berkali-kali.
        //
        // STEP 7:
        // Buat SearchResult untuk dokumen tersebut.
        //
        // STEP 8:
        // Return ranking.
        return null;
    }

    private double computeBIMWeight(String term, InvertedIndex index) {
        // Menghitung bobot term BIM.
        //
        // Rumus tanpa relevance judgment:
        // wt = log(0.5 * N / Nt)
        //
        // N  = total dokumen
        // Nt = document frequency term
        //
        // STEP 1:
        // Ambil N dari index.
        //
        // STEP 2:
        // Ambil Nt dari index.
        //
        // STEP 3:
        // Jika Nt = 0, return 0.
        //
        // STEP 4:
        // Hitung bobot.
        //
        // STEP 5:
        // Return bobot.
        return 0.0;
    }
}