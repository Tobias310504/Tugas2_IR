package ir.io;

import ir.evaluation.EvaluationResult;
import ir.retrieval.SearchResult;
import java.util.List;
import java.util.Locale;

public class ResultWriter {

    public void printRanking(String modelName, List<SearchResult> results) {
       //tampilkan judul hasil ranking berdasarkan nama model
       System.out.println("===== " + modelName + " Results =====");
       //kalau results null atau kosong maka tampilkan pesan tidak ada hasil
       if (results == null || results.isEmpty()) {
           System.out.println("Tidak ada hasil ranking");
           return;
       }
       //loop semua SearchResult berdasarkan urutan ranking
       for (int i = 0; i < results.size(); i++) {
           //ambil SearchResult pada index ke-i
           SearchResult result = results.get(i);

           //rank dimulai dari 1
           int rank = i + 1;

           //tampilkan rank, document id, dan score ke terminal
           System.out.println(String.format(
                   Locale.US,
                   "%d. %s : %.6f",
                   rank,
                   result.getDocumentId(),
                   result.getScore()
           ));
       }
    }

    public void printEvaluation(List<EvaluationResult> results) {
       //kalau results null atau kosong maka tampilkan pesan tidak ada evaluasi
       if (results == null || results.isEmpty()) {
           System.out.println("Tidak ada hasil evaluasi");
           return;
       }
       //loop semua hasil evaluasi
       for (EvaluationResult result : results) {
           //tampilkan nilai evaluasi setiap query ke terminal
           System.out.println(String.format(
                   Locale.US,
                   "%s | %s | P@%d = %.6f | R@%d = %.6f | F1 = %.6f | AP = %.6f",
                   result.getModelName(),
                   result.getQueryId(),
                   result.getTopK(),
                   result.getPrecisionAtK(),
                   result.getTopK(),
                   result.getRecallAtK(),
                   result.getF1AtK(),
                   result.getAveragePrecision()
           ));
       }
    }

    public void printMap(String modelName, double map) {
       //tampilkan nilai MAP untuk satu model ke terminal
       System.out.println(String.format(Locale.US, "MAP %s : %.6f", modelName, map));
    }
}
