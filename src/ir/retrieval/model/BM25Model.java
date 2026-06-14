package ir.retrieval.model;

import ir.index.InvertedIndex;
import ir.preprocessing.TextPreprocessor;
import ir.retrieval.RetrievalModel;
import ir.retrieval.SearchResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BM25Model implements RetrievalModel {

    private TextPreprocessor preprocessor;
    private double k1;
    private double b;
    //konstruktor default
    public BM25Model(TextPreprocessor preprocessor) {
        this.preprocessor = preprocessor;
        this.k1 = 1.5;
        this.b = 0.75;
    }
    //konstruktor kalau ingin mengkonfigurasi 
    public BM25Model(TextPreprocessor preprocessor, double k1, double b){
        this.preprocessor = preprocessor;
        this.k1 = k1;
        this.b = b;
    }

    @Override
    public String getModelName() {
        // Mengembalikan nama model.
        return "BM25";
    }
    //method untuk menghitung ranking menggunakan BM25
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
                // ambil tf dan df nya
                int tf = index.getTermFrequency(term, doc);
                int df = index.getDocumentFrequency(term);
                //ambil total dokumen
                int totalDokumen = index.getTotalDocuments();
                //ambil panjang dokumen
                int documentLength = index.getDocumentLength(doc);
                //ambil panjang avg dari dokumen
                double documentAvgLength = index.getAverageDocumentLength();
                // update skor untuk dokumen saat ini
                skor += computeTermScore(tf, df, totalDokumen, documentLength, documentAvgLength);

            }
            //masukan skor dokumen ke list
            skorDokumen.add(new SearchResult(doc, skor));
        }
        //sorting list skorDokumen
        Collections.sort(skorDokumen);
        //return list skorDokumennya
        return skorDokumen;
    }
    //method untuk menghitung skor untuk 1 term pada BM25
    private double computeTermScore(int tf, int df, int totalDocuments, int documentLength, double averageDocumentLength) {
        //kalau term tidak muncul di dokumen, maka skor 0.0
        if(tf <= 0){
            return 0.0;
        }
        //cek juga apakah df, total dokumen, dan avg panjang dokumen 0 atau tidak, agar pembagi tidak 0 kembalikan skor 0
        if(df <= 0 || totalDocuments <= 0 || averageDocumentLength <= 0){
            return 0.0;
        }
        //hitung idfnya
        double idf = computeIdf(df, totalDocuments);
        //hitung normalisasi panjang dokumen
        double normalizeLength = 1 - b + b * ((double) documentLength / averageDocumentLength);
        //hitung numeratornya
        double nurmerator = tf * (k1 + 1);
        //hitung denominatornya
        double denominator = tf + k1 * normalizeLength;
        //return skor term
        double skor = idf * (nurmerator / denominator);
        return skor;
    }
    //method untuk menghitung idf untuk BM25
    private double computeIdf(int df, int totalDocuments) {
        //menghitung IDF untuk BM25
        double idf = Math.log(((totalDocuments - df + 0.5)/(df + 0.5)) + 1);
        return idf;
    }
}