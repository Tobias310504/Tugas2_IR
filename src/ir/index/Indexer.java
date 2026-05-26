package ir.index;

import ir.document.Document;
import ir.preprocessing.TextPreprocessor;

import java.util.List;

public class Indexer {

    private TextPreprocessor preprocessor;

    public Indexer(TextPreprocessor preprocessor) {
        // Menyimpan object TextPreprocessor.
        //
        // Indexer membutuhkan preprocessor untuk membersihkan dokumen
        // sebelum dimasukkan ke inverted index.
    }

    public InvertedIndex buildIndex(List<Document> documents) {
        // Membangun inverted index dari kumpulan dokumen.
        //
        // STEP 1:
        // Buat object InvertedIndex kosong.
        //
        // STEP 2:
        // Loop setiap Document.
        //
        // STEP 3:
        // Ambil documentId dan content.
        //
        // STEP 4:
        // Preprocess content menjadi List<String> tokens.
        //
        // STEP 5:
        // Hitung document length dari jumlah token.
        //
        // STEP 6:
        // Simpan document length ke InvertedIndex.
        //
        // STEP 7:
        // Untuk setiap token, panggil index.addTerm(token, documentId).
        //
        // STEP 8:
        // Setelah semua dokumen selesai, hitung average document length.
        //
        // STEP 9:
        // Return InvertedIndex.
        return null;
    }
}