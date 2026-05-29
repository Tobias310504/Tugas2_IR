package ir.preprocessing;

import java.util.List;

public class TextPreprocessor {

    private Tokenizer tokenizer;
    private StopwordRemover stopwordRemover;
    private Stemmer stemmer;
    // method untuk menginisialisasikan object Tokenizer, StopwordRemover, dan Stemmer
    public TextPreprocessor() {
        this.tokenizer = new Tokenizer();
        this.stemmer = new Stemmer();
        this.stopwordRemover = new StopwordRemover();
    }
    // method untuk melakukan preprocessing
    public List<String> preprocess(String text) {
        //cek apakah text null
        if(text == null){
            text = "";
        }
        // Ubah teks menjadi lowercase
        String loweredText = text.toLowerCase();
        // tokenisasi teks menggunakan Tokenizer
        List<String> tokens = tokenizer.tokenize(loweredText);
        // hapus stopword menggunakan StopwordRemover
        List<String> filteredTokens = stopwordRemover.remove(tokens);
        // lakukan stemming menggunakan Stemmer
        List<String> stemmedToken = stemmer.stemAll(filteredTokens);
        // Return token hasilnya
        return stemmedToken;
    }
}