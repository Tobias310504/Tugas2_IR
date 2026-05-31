package ir.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InvertedIndex {
    private Map<String, List<Posting>> index;
    private Map<String, Integer> documentLengths;
    private double averageDocumentLength;

    public InvertedIndex() {
        this.index = new HashMap<>();
        this.documentLengths = new HashMap<>();
        this.averageDocumentLength = 0;
    }
    // method untuk menambahkan term ke inverted index
    public void addTerm(String term, String documentId) {
        // cek apakah term sudah ada di index
        if(!index.containsKey(term)){
            //inisialisasi postinglist
            List<Posting> postingList = new ArrayList<>();
            //masukan dokumen id baru ke posting listnya
            postingList.add(new Posting(documentId));
            //baru masukan ke inverted index nya
            index.put(term, postingList);
        }
        //kalau term sudah ada, ambil posting list-nya
        List<Posting> postingList = index.get(term);
        // cek apakah documentId sudah ada di posting list
        for(Posting posting : postingList){
            if(posting.getDocumentId().equals(documentId)){
                //kalau sudah ada kita tambahkan term frequencynya untuk dokumen tersebut
                posting.incrementTermFrequency();
            }
        }
        //kalau belum ada, buat Posting baru
        postingList.add(new Posting(documentId));
    }
    // method untuk mengambil tf suatu term pada dokumen tertentu
    public int getTermFrequency(String term, String documentId) {
        //kalau term tidak ada, return 0
        if(!index.containsKey(term)){
            return 0;
        }
        //ambil posting list dari term tersebut
        List<Posting> postingList = index.get(term);

        //cari documentId di dalam posting list
        for(Posting posting : postingList){
            //kalau ketemu ambil termfrequency dari dokumen tersebut
            if(posting.getDocumentId().equals(documentId)){
                return posting.getTermFrequency();
            }
        }
        // jika dokumen tidak mengandung term, return 0
        return 0;
    }
    // method untuk mengambil df dari sebuah term
    public int getDocumentFrequency(String term) {
        //cek apakah term ada di inverted index atau tidak
        if(!index.containsKey(term)){
            return 0;
        }
        //ambil posting list dari term tersebut
        List<Posting> postingList = index.get(term);
        // df = jumlah dokumen yang mengandung term tersebut.
        return postingList.size();
    }
    //method untuk menyimpan panjang dokumen setelah preprocessing
    public void setDocumentLength(String documentId, int length) {
        // Panjang dokumen = jumlah token bersih.
        documentLengths.put(documentId, length);
    }
    //method untuk mencatat panjang document berdasarkan documentIdnya
    public int getDocumentLength(String documentId) {
        //kalau documentId tidak ada di documentLengths 
        if(!documentLengths.containsKey(documentId)){
            return 0;
        }
        // Mengembalikan panjang dokumen tertentu.
        return documentLengths.get(documentId);
    }
    //method untuk menghitung rata-rata panjang dokumen.
    public void calculateAverageDocumentLength() {
        if(documentLengths.isEmpty()){
            averageDocumentLength = 0.0;
            return;
        }
        int totalLength = 0;
        //jumlahkan semua document length
        for(int length : documentLengths.values()){
            totalLength += length;
        }
        //bagi dengan jumlah dokumen
        averageDocumentLength = (double) totalLength / documentLengths.size();
    }
    //method untuk mengambil average document
    public double getAverageDocumentLength() {
       return averageDocumentLength;
    }
    //method untuk mengembalikan jumlah total dokumen
    public int getTotalDocuments() {
        return documentLengths.size();
    }
    //method untuk mengembalikan semua ID dokumen
    public Set<String> getAllDocumentIds() {
        return new HashSet<>(documentLengths.keySet());
    }
    //method untuk mengembalikan posting list untuk term tertentu
    public List<Posting> getPostingList(String term) {
        //kalau term tidak ada di index, return arraylist kosong
        if(!index.containsKey(term)){
            return new ArrayList<>();
        }
        //kalau term ada, return posting list milik term tersebut
        return index.get(term);
    }
    //method untuk mengembalikan banyaknya vocabulary
    public int getVocabularySize() {
        return index.size();
    }
}