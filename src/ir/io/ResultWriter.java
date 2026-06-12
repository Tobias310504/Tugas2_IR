package ir.io;

import ir.evaluation.EvaluationResult;
import ir.retrieval.SearchResult;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ResultWriter {

    public void writeRanking(String filePath, List<SearchResult> results) {
       //buka file output ranking
       try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
           //tulis header csv untuk ranking
           writer.write("rank,documentId,score");
           //pindah ke baris berikutnya
           writer.newLine();
           //kalau results null maka tidak ada data yang ditulis
           if (results == null) {
               return;
           }
           //loop semua SearchResult berdasarkan urutan ranking
           for (int i = 0; i < results.size(); i++) {
               //ambil SearchResult pada index ke-i
               SearchResult result = results.get(i);

               //rank dimulai dari 1
               int rank = i + 1;

               //buat satu baris csv berisi rank, documentId, dan score
               String line = String.format(
                       Locale.US,
                       "%d,%s,%.6f",
                       rank,
                       result.getDocumentId(),
                       result.getScore()
               );
               //tulis baris ranking ke file
               writer.write(line);
               //pindah ke baris berikutnya
               writer.newLine();
           }
       } catch (IOException e) {
           //tampilkan error kalau file gagal ditulis
           System.out.println("Error writing ranking file: " + e.getMessage());
       }
    }

    public void writeEvaluation(String filePath, List<EvaluationResult> results) {
       //buka file output evaluasi
       try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
           //tulis header csv untuk evaluasi
           writer.write("model,queryId,topK,precisionAtK,recallAtK,f1AtK,averagePrecision");
           //pindah ke baris berikutnya
           writer.newLine();
           //kalau results null maka tidak ada data yang ditulis
           if (results == null) {
               return;
           }
           //loop semua hasil evaluasi
           for (EvaluationResult result : results) {
               //buat satu baris csv dari object EvaluationResult
               String line = String.format(
                       Locale.US,
                       "%s,%s,%d,%.6f,%.6f,%.6f,%.6f",
                       result.getModelName(),
                       result.getQueryId(),
                       result.getTopK(),
                       result.getPrecisionAtK(),
                       result.getRecallAtK(),
                       result.getF1AtK(),
                       result.getAveragePrecision()
               );
               //tulis baris evaluasi ke file
               writer.write(line);
               //pindah ke baris berikutnya
               writer.newLine();
           }
       } catch (IOException e) {
           //tampilkan error kalau file gagal ditulis
           System.out.println("Error writing evaluation file: " + e.getMessage());
       }
    }
}
