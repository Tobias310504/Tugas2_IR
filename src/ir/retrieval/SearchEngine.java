package ir.retrieval;

import ir.index.InvertedIndex;
import java.util.ArrayList;
import java.util.List;

public class SearchEngine {

    private InvertedIndex index;

    public SearchEngine(InvertedIndex index) {
        //simpan InvertedIndex yang sudah dibangun
        this.index = index;
    }
    //method untuk menjalankan pencarian dengan model tertentu
    public List<SearchResult> search(String query, RetrievalModel model, int topK) {
        //panggil model.rank(query, index)
        List<SearchResult> results = model.rank(query, index);
        //ambil topK dokumen
        if(topK <= 0 || topK >= results.size()){
            return results;
        }
        //kembalikan hasil topK
        return new ArrayList<>(results.subList(0,topK));
    }
}