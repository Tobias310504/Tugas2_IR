package ir.io;

import ir.retrieval.SearchResult;
import ir.evaluation.EvaluationResult;

import java.util.List;

public class ResultWriter {

    public void writeRanking(String filePath, List<SearchResult> results) {
        // Menulis hasil ranking ke file output.
        //
        // Format output:
        // rank, documentId, score
        //
        // STEP 1:
        // Buat file output.
        //
        // STEP 2:
        // Tulis header.
        //
        // STEP 3:
        // Loop semua SearchResult.
        //
        // STEP 4:
        // Tulis rank, documentId, dan score.
    }

    public void writeEvaluation(String filePath, List<EvaluationResult> results) {
        // Menulis hasil evaluasi ke file CSV.
        //
        // Format output:
        // model, queryId, precisionAtK, recallAtK, averagePrecision
        //
        // STEP 1:
        // Buat file output.
        //
        // STEP 2:
        // Tulis header.
        //
        // STEP 3:
        // Loop semua EvaluationResult.
        //
        // STEP 4:
        // Tulis hasil evaluasi.
    }
}