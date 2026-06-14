package ir.io;

import ir.evaluation.EvaluationResult;
import ir.retrieval.SearchResult;
import java.util.List;
import java.util.Locale;

public class ResultWriter {

    public void printRanking(String modelName, List<SearchResult> results) {
        //judul hasil ranking berdasarkan nama model
        System.out.println();
        System.out.println("===== " + modelName + " Ranking =====");
        //kalau results null atau kosong maka tampilkan pesan tidak ada hasil
        if(results == null || results.isEmpty()){
            System.out.println("Belum ada hasil");
            return;
        }
        System.out.println("----------------------------------------");
        System.out.printf("%-6s %-12s %-12s%n", "Rank", "Document", "Score");
        System.out.println("----------------------------------------");
        //loop semua SearchResult berdasarkan urutan ranking
        for(int i = 0 ; i < results.size(); i++){
            //ambil SearchResult pada index ke-i
            SearchResult result = results.get(i);
            //buat nilai rank mulai dari 1
            int rank = i + 1;
            //ambil documentId dari SearchResult
            String documentId = result.getDocumentId();
            //ambil score dari SearchResult
            double score = result.getScore();
            //print rank, documentId, dan score
            System.out.printf(Locale.US, "%-6d %-12s %-12.4f%n", rank, documentId, score);
        }
        System.out.println("----------------------------------------");
    }

    public void printEvaluation(List<EvaluationResult> results) {
        //kalau results null atau kosong maka print "tidak ada evaluasi"
        if(results == null || results.isEmpty()){
            System.out.println("Tidak ada evaluasi");
            return;
        }
        System.out.println();
        System.out.println("===== " + results.get(0).getModelName() + " Evaluation =====");
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.printf("%-8s %-6s %-10s %-10s %-10s %-10s %-10s%n", "Query", "K", "P@K", "R@K", "F1", "AP", "11-Point");
        System.out.println("----------------------------------------------------------------------------------------");
        //loop semua EvaluationResult
        for(EvaluationResult res : results){
            //ambil id query
            String queryId = res.getQueryId();
            //ambil topK
            int topK = res.getTopK();
            //ambil precision
            double precision = res.getPrecisionAtK();
            //ambil recall
            double recall = res.getRecallAtK();
            //ambil f1
            double f1 = res.getF1AtK();
            //ambil average precision
            double averagePrecision = res.getAveragePrecision();
            //ambil 11-point average precision
            double elevenPointAverage = res.getElevenPointAverage();
            //print hasil evaluasi per query
            System.out.printf(
                    Locale.US,
                    "%-8s %-6d %-10.4f %-10.4f %-10.4f %-10.4f %-10.4f%n",
                    queryId,
                    topK,
                    precision,
                    recall,
                    f1,
                    averagePrecision,
                    elevenPointAverage
            );
        }
        System.out.println("----------------------------------------------------------------------------------------");
    }

    public void printMap(String modelName, double map) {
        //print nilai MAP dari model yang dipakai
        System.out.printf(Locale.US, "MAP %-14s : %.4f%n", modelName, map);
    }
}
