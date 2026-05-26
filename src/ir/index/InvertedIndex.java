package ir.index;

import java.util.List;
import java.util.Set;

public class InvertedIndex {

    public void addTerm(String term, String documentId) {
        // Menambahkan term ke inverted index.
        //
        // STEP 1:
        // Cek apakah term sudah ada di index.
        //
        // STEP 2:
        // Jika belum ada, buat posting list baru.
        //
        // STEP 3:
        // Jika term sudah ada, ambil posting list-nya.
        //
        // STEP 4:
        // Cek apakah documentId sudah ada di posting list.
        //
        // STEP 5:
        // Jika sudah ada, increment term frequency.
        //
        // STEP 6:
        // Jika belum ada, buat Posting baru.
    }

    public int getTermFrequency(String term, String documentId) {
        // Mengambil tf suatu term pada dokumen tertentu.
        //
        // Jika term tidak ada, return 0.
        // Jika dokumen tidak mengandung term, return 0.
        return 0;
    }

    public int getDocumentFrequency(String term) {
        // Mengambil df dari sebuah term.
        //
        // df = jumlah dokumen yang mengandung term tersebut.
        return 0;
    }

    public void setDocumentLength(String documentId, int length) {
        // Menyimpan panjang dokumen setelah preprocessing.
        //
        // Panjang dokumen = jumlah token bersih.
    }

    public int getDocumentLength(String documentId) {
        // Mengembalikan panjang dokumen tertentu.
        return 0;
    }

    public void calculateAverageDocumentLength() {
        // Menghitung rata-rata panjang dokumen.
        //
        // STEP 1:
        // Jumlahkan semua document length.
        //
        // STEP 2:
        // Bagi dengan jumlah dokumen.
        //
        // STEP 3:
        // Simpan ke averageDocumentLength.
    }

    public double getAverageDocumentLength() {
        // Mengembalikan avgdl.
        return 0.0;
    }

    public int getTotalDocuments() {
        // Mengembalikan jumlah total dokumen.
        return 0;
    }

    public Set<String> getAllDocumentIds() {
        // Mengembalikan semua ID dokumen.
        return null;
    }

    public List<Posting> getPostingList(String term) {
        // Mengembalikan posting list untuk term tertentu.
        return null;
    }
}