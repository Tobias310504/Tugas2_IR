package ir.io;

public class ConsoleInput {

    public String readQuery() {
        // Membaca query yang diketik user melalui terminal.
        //
        // STEP 1:
        // Tampilkan pesan "Enter query:".
        //
        // STEP 2:
        // Ambil input user.
        //
        // STEP 3:
        // Return query sebagai String.
        return null;
    }

    public String readModelChoice() {
        // Membaca pilihan model dari terminal.
        //
        // STEP 1:
        // Tampilkan pilihan:
        // 1. BIM
        // 2. Two-Poisson
        // 3. BM25
        // 4. BM10
        //
        // STEP 2:
        // Ambil pilihan user.
        //
        // STEP 3:
        // Return pilihan tersebut.
        return null;
    }

    public int readTopK() {
        // Membaca jumlah hasil ranking yang ingin ditampilkan.
        //
        // Contoh:
        // User input 10 berarti tampilkan top 10 dokumen.
        return 0;
    }
}