package ir.retrieval.model;

import ir.index.InvertedIndex;
import ir.preprocessing.TextPreprocessor;
import ir.retrieval.RetrievalModel;
import ir.retrieval.SearchResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TwoPoissonModel implements RetrievalModel {

    private TextPreprocessor preprocessor;
    private double k;

    public TwoPoissonModel(TextPreprocessor preprocessor) {
        //simpan TextPreprocessor
        this.preprocessor = preprocessor;
        //set nilai k
        this.k = 1.0;
    }

    public TwoPoissonModel(TextPreprocessor preprocessor, double k) {
        //simpan TextPreprocessor
        this.preprocessor = preprocessor;
        //simpan nilai k dari input user
        this.k = k;
    }

    //method untuk mengembalikan nama model
    @Override
    public String getModelName() {
        return "Two-Poisson";
    }

    @Override
    public List<SearchResult> rank(String query, InvertedIndex index) {
        //inisialisasi list kosong untuk menghitung skor dokumen
        List<SearchResult> skorDokumen = new ArrayList<>();
        //buat list untuk menampung tokenisasi dari query
        List<String> tokenQuery = preprocessor.preprocess(query);
        // Loop semua dokumen.
        for(String doc : index.getAllDocumentIds()){
            //set skor awal itu 0.0
            double skor = 0.0;
            //lalu loop seluruh query yang sudah di tokenisasi
            for(String term : tokenQuery){
                // ambil tf dan weight dokumennya nya
                int tf = index.getTermFrequency(term, doc);
                //hitung weightnya 
                double weight = computeWeight(term, index);
                // update skor untuk dokumen saat ini
                skor += computeTermScore(tf, weight);
            }
            //masukan skor dokumen ke list
            skorDokumen.add(new SearchResult(doc, skor));
        }
        //sorting list skorDokumen
        Collections.sort(skorDokumen);
        //return list skorDokumennya
        return skorDokumen;
    }
    //method untuk menghitung skor satu term pada Two-Poisson
    private double computeTermScore(int tf, double weight) {
        ///kalau term tidak muncul di dokumen, maka skor 0.0
        if(tf <= 0){
            return 0.0;
        }
        //hitung numerator = tf * (k + 1) * weight
        double nurmerator = tf * (k + 1) * weight;
        //hitung denominator = tf + k
        double denominator = tf + k;
        //hitung skor nya lalu return skor nya
        double skor = nurmerator / denominator;
        return skor;
    }
    //method untuk menghitung bobot term
    private double computeWeight(String term, InvertedIndex index) {
        //simpan total dokumen
        int totalDocuments = index.getTotalDocuments();
        //simpan document frequency term
        int documentFreqTerms = index.getDocumentFrequency(term);

        //kalau dokumen frekuensi 0 atau total dokumen 0 maka return weight 0
        if(documentFreqTerms == 0 || totalDocuments == 0){
            return 0.0;
        }
        // hitung weightnya
        double weight = Math.log((0.5 * totalDocuments)/ documentFreqTerms);
        //kembalikan weight nya
        return weight;
    }
}
