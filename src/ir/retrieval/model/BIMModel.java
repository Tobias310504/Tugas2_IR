package ir.retrieval.model;

import ir.index.InvertedIndex;
import ir.preprocessing.TextPreprocessor;
import ir.retrieval.RetrievalModel;
import ir.retrieval.SearchResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BIMModel implements RetrievalModel {

    private TextPreprocessor preprocessor;

    public BIMModel(TextPreprocessor preprocessor) {
        //Menyimpan TextPreprocessor untuk memproses query
        this.preprocessor = preprocessor;
    }

    @Override
    public String getModelName() {
        return "BIM";
    }

    @Override
    public List<SearchResult> rank(String query, InvertedIndex index) {
        //List untuk menyimpan hasil ranking dokumen
        List<SearchResult> results = new ArrayList<SearchResult>();

        //Preprocess query
        List<String> queryTokens = preprocessor.preprocess(query);

        //BIM bersifat binary, jadi term query dibuat unik
        Set<String> uniqueTerms = new HashSet<String>(queryTokens);

        //Loop semua dokumen
        for (String documentId : index.getAllDocumentIds()) {
            double score = 0.0;

            //Untuk setiap term query, cek apakah term muncul di dokumen
            for (String term : uniqueTerms) {
                int tf = index.getTermFrequency(term, documentId);

                //Karena BIM binary, yang dicek hanya ada/tidak ada
                if (tf > 0) {
                    score += computeBIMWeight(term, index);
                }
            }

            //Simpan skor dokumen
            results.add(new SearchResult(documentId, score));
        }

        //Urutkan berdasarkan skor tertinggi
        Collections.sort(results);

        return results;
    }

    private double computeBIMWeight(String term, InvertedIndex index) {
        //N = total dokumen
        int totalDocuments = index.getTotalDocuments();

        //Nt = jumlah dokumen yang mengandung term
        int documentFrequency = index.getDocumentFrequency(term);

        //Kalau term tidak muncul atau dokumen kosong, bobot 0
        if (totalDocuments <= 0 || documentFrequency <= 0) {
            return 0.0;
        }

        //Rumus BIM sederhana tanpa relevance feedback: wt = log((0.5 * N) / Nt)
        return Math.log((0.5 * totalDocuments) / documentFrequency);
    }
}