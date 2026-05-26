package ir.preprocessing;

import java.util.List;

public class TextPreprocessor {

    private Tokenizer tokenizer;
    private StopwordRemover stopwordRemover;
    private Stemmer stemmer;

    public TextPreprocessor() {
        // Membuat object Tokenizer, StopwordRemover, dan Stemmer.
        //
        // Class ini bertugas menggabungkan semua tahap preprocessing.
    }

    public List<String> preprocess(String text) {
        // Melakukan preprocessing lengkap.
        //
        // STEP 1:
        // Ubah teks menjadi lowercase.
        //
        // STEP 2:
        // Tokenisasi teks menggunakan Tokenizer.
        //
        // STEP 3:
        // Hapus stopword menggunakan StopwordRemover.
        //
        // STEP 4:
        // Lakukan stemming menggunakan Stemmer.
        //
        // STEP 5:
        // Return token akhir.
        return null;
    }
}