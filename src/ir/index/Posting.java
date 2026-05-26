package ir.index;

public class Posting {

    private String documentId;
    private int termFrequency;

    public Posting(String documentId) {
        // Menyimpan ID dokumen.
        // Set termFrequency awal menjadi 1.
    }

    public String getDocumentId() {
        // Mengembalikan ID dokumen.
        return null;
    }

    public int getTermFrequency() {
        // Mengembalikan frekuensi term pada dokumen ini.
        return 0;
    }

    public void incrementTermFrequency() {
        // Menambah termFrequency sebesar 1.
        //
        // Dipanggil ketika term yang sama muncul lagi
        // pada dokumen yang sama.
    }
}