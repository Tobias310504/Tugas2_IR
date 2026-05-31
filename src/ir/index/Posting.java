package ir.index;

public class Posting {

    private String documentId;
    private int termFrequency;

    public Posting(String documentId) {
        // simpan ID dokumen
        this.documentId = documentId;
        //set termFrequency 1 di awal
        this.termFrequency = 1;
    }
    // method untuk mengembalikan ID dokumen dalam bentuk string
    public String getDocumentId() {
        return documentId;
    }
    // method untuk mengembalikan frekuensi term pada sebuah dokumen
    public int getTermFrequency() {
        return termFrequency;
    }
    //method yang dipanggil ketika term yang sama muncul lgi, lalu akan menambahkan term frequency pada dokumennya
    public void incrementTermFrequency() {
        termFrequency++;
    }
}