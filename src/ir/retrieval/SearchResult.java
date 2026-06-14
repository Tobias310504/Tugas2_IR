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
        int scoreCompare = Double.compare(other.getScore(), this.getScore());
        //kalau score berbeda maka urutkan berdasarkan score
        if(scoreCompare != 0){
            return scoreCompare;
        }
        //kalau score sama maka urutkan berdasarkan documentId agar ranking stabil
        return compareDocumentId(this.documentId, other.getDocumentId());
    }

    private int compareDocumentId(String firstDocumentId, String secondDocumentId) {
        try {
            //ubah documentId menjadi angka agar urutan 10 tidak muncul sebelum 2
            int firstId = Integer.parseInt(firstDocumentId);
            int secondId = Integer.parseInt(secondDocumentId);
            //urutkan documentId dari kecil ke besar
            return Integer.compare(firstId, secondId);
        } catch (NumberFormatException e) {
            //kalau documentId bukan angka maka urutkan sebagai String
            return firstDocumentId.compareTo(secondDocumentId);
        }
    }
}
