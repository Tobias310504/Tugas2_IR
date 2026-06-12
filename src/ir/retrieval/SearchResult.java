package ir.retrieval;

public class SearchResult implements Comparable<SearchResult> {

    private String documentId;
    private double score;

    public SearchResult(String documentId, double score) {
        //simpan ID dokumen dan skor hasil retrieval
        this.documentId = documentId;
        this.score = score;
    }
    //method untuk mengembalikan ID dokumen
    public String getDocumentId() {
        return documentId;
    }
    //method untuk mengembalikan skor dokumen
    public double getScore() {
        return score;
    }
    //mengurutkan SearchResult berdasarkan skor descending
    @Override
    public int compareTo(SearchResult other) {
        //membandingkan score milik other dengan score milik objek saat ini, dokumen dengan score yang besar akan muncul duluan
        return Double.compare(other.getScore(), this.getScore());
    }
}