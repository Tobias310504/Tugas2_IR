package ir.index;

public class IndexStatistics {
    //method untuk menghitung jumlah term unik dalam index
    public int getVocabularySize(InvertedIndex index) {
        return index.getVocabularySize();
    }
    //method untuk enampilkan statistik index
    public void printSummary(InvertedIndex index) {
        System.out.println("===== Index Summary =====");
        System.out.println("Total dokumen: " + index.getTotalDocuments());
        System.out.println("Average document length: " + index.getAverageDocumentLength());
        System.out.println("Vocabulary size: " + getVocabularySize(index));
        System.out.println("=========================");
    }
}