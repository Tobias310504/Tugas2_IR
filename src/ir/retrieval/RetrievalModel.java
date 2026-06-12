package ir.retrieval;

import ir.index.InvertedIndex;
import java.util.List;
// ini interface yang digunakan oleh semua model 
public interface RetrievalModel {

    String getModelName();

    List<SearchResult> rank(String query, InvertedIndex index);
}