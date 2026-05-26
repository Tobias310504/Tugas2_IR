package ir.index;

public class IndexStatistics {

    public int getVocabularySize(InvertedIndex index) {
        // Menghitung jumlah term unik dalam index.
        //
        // Method ini opsional.
        // Bisa dipakai untuk menampilkan ringkasan index.
        return 0;
    }

    public void printSummary(InvertedIndex index) {
        // Menampilkan statistik index.
        //
        // Contoh:
        // total dokumen
        // rata-rata panjang dokumen
        // jumlah vocabulary
    }
}