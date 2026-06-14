package ir.retrieval.model;

import ir.index.InvertedIndex;
import ir.preprocessing.TextPreprocessor;
import ir.retrieval.RetrievalModel;
import ir.retrieval.SearchResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BM10Model implements RetrievalModel {

    private TextPreprocessor preprocessor;
    private double k1;

    public BM10Model(TextPreprocessor preprocessor) {
        //Menyimpan TextPreprocessor
        this.preprocessor = preprocessor;

        //Parameter BM10
        this.k1 = 1.5;
    }

    public BM10Model(TextPreprocessor preprocessor, double k1) {
        //Menyimpan TextPreprocessor
        this.preprocessor = preprocessor;

        //Parameter BM10 dari input user
        this.k1 = k1;
    }

    @Override
    public String getModelName() {
        return "BM10";
    }

    @Override
    public List<SearchResult> rank(String query, InvertedIndex index) {
        //List untuk menyimpan skor setiap dokumen
        List<SearchResult> results = new ArrayList<SearchResult>();

        //Preprocess query
        List<String> queryTokens = preprocessor.preprocess(query);

        //Ambil total dokumen
        int totalDocuments = index.getTotalDocuments();

        //Loop semua dokumen
        for (String documentId : index.getAllDocumentIds()) {
            double score = 0.0;

            for (String term : queryTokens) {//Hitung skor untuk setiap term query
                int tf = index.getTermFrequency(term, documentId);
                int df = index.getDocumentFrequency(term);

                score += computeTermScore(tf, df, totalDocuments);
            }

            //Simpan skor dokumen
            results.add(new SearchResult(documentId, score));
        }

        //Urutkan berdasarkan skor tertinggi
        Collections.sort(results);

        return results;
    }

    private double computeTermScore(int tf, int df, int totalDocuments) {
        //Kalau term tidak muncul, skor 0
        if (tf <= 0) {
            return 0.0;
        }

        //Hindari pembagian yg tidak valid
        if (df <= 0 || totalDocuments <= 0) {
            return 0.0;
        }

        //Hitung IDF
        double idf = computeIdf(df, totalDocuments);

        double numerator = tf * (k1 + 1);
        double denominator = tf + k1;

        return idf * (numerator / denominator);
    }

    private double computeIdf(int df, int totalDocuments) {
        return Math.log(((totalDocuments - df + 0.5) / (df + 0.5)) + 1);
    }
}