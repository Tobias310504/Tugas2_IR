package ir.io;

import ir.evaluation.RelevanceJudgment;
import java.util.List;

public class QrelsReader {

    public List<RelevanceJudgment> readQrels(String filePath) {
        // Membaca file qrels.txt.
        //
        // Format:
        // Q1 <tab> DOC1 <tab> 1
        // Q1 <tab> DOC2 <tab> 0
        //
        // STEP 1:
        // Buka qrels.txt.
        //
        // STEP 2:
        // Baca baris per baris.
        //
        // STEP 3:
        // Ambil queryId, documentId, dan relevance label.
        //
        // STEP 4:
        // Buat object RelevanceJudgment.
        //
        // STEP 5:
        // Masukkan ke List<RelevanceJudgment>.
        //
        // STEP 6:
        // Return list.
        return null;
    }
}