package ir.retrieval;

import ir.index.InvertedIndex;
import java.util.List;

public interface RetrievalModel {

    String getModelName();

    List<SearchResult> rank(String query, InvertedIndex index);

    // Interface ini dipakai oleh semua model:
    // - BIMModel
    // - TwoPoissonModel
    // - BM25Model
    // - BM10Model
    //
    // Tujuannya agar SearchEngine bisa menjalankan model apa pun
    // dengan cara yang sama.
}