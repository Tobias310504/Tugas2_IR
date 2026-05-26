package ir.retrieval;

public class SearchResult implements Comparable<SearchResult> {

    private String documentId;
    private double score;

    public SearchResult(String documentId, double score) {
        // Menyimpan ID dokumen dan skor hasil retrieval.
    }

    public String getDocumentId() {
        // Mengembalikan ID dokumen.
        return null;
    }

    public double getScore() {
        // Mengembalikan skor dokumen.
        return 0.0;
    }

    @Override
    public int compareTo(SearchResult other) {
        // Mengurutkan SearchResult berdasarkan skor descending.
        //
        // Dokumen dengan skor terbesar harus muncul di ranking atas.
        return 0;
    }
}