package ir.index;

import ir.document.Document;
import ir.preprocessing.TextPreprocessor;
import java.util.List;

public class Indexer {

    private TextPreprocessor preprocessor;

    public Indexer(TextPreprocessor preprocessor) {
        // menyimpan object TextPreprocessor
        this.preprocessor = preprocessor;
    }
    // method untuk membangun inverted index dari kumpulan dokumen
    public InvertedIndex buildIndex(List<Document> documents) {
        //buat object inverted index kosong.
        InvertedIndex index = new InvertedIndex();
        //loop setiap document
        for(Document document : documents){
            //ambil documentId dan content
            String documentId = document.getId();
            String content = document.getContent();
            //prreprocess content menjadi token
            List<String> tokens = preprocessor.preprocess(content);
            //simpan panjang dokumen
            index.setDocumentLength(documentId, tokens.size());
            //masukkan setiap token ke inverted index
            for (String token : tokens) {
                index.addTerm(token, documentId);
            }
        }
        //hitung rata-rata panjang dokumen setelah semua dokumen sudah dicatat
        index.calculateAverageDocumentLength();
        //return inverted index.
        return index;
    }
}