package ir.io;

import ir.evaluation.RelevanceJudgment;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QrelsReader {

    public List<RelevanceJudgment> readQrels(String filePath) {
        //inisialisasikan list untuk menyimpan relevance judgment
        List<RelevanceJudgment> judgments = new ArrayList<RelevanceJudgment>();
        //buka file qrels.txt
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            //siapkan variable untuk membaca file baris per baris
            String line;
            //baca file sampai tidak ada baris lagi
            while ((line = reader.readLine()) != null) {
                //trim dulu barisnya
                line = line.trim();
                //kalau baris kosong di skip
                if (line.isEmpty()) {
                    continue;
                }
                //format qrels adalah queryId|documentId|relevance
                String[] parts = line.split("\\|", 3);
                //kalau format qrels tidak sesuai maka skip baris tersebut
                if (parts.length < 3) {
                    continue;
                }
                //ambil query id dari parts index 0
                String queryId = parts[0].trim();
                //ambil document id dari parts index 1
                String documentId = parts[1].trim();
                //ambil relevance dari parts index 2
                String relevanceText = parts[2].trim();

                //kalau ada data yang kosong maka skip baris tersebut
                if (queryId.isEmpty() || documentId.isEmpty() || relevanceText.isEmpty()) {
                    continue;
                }
                try {
                    //ubah relevance dari string menjadi integer
                    int relevance = Integer.parseInt(relevanceText);
                    //buat object RelevanceJudgment untuk menyimpan data qrels
                    RelevanceJudgment judgment = new RelevanceJudgment(queryId, documentId, relevance);
                    //masukkan judgment ke list
                    judgments.add(judgment);
                } catch (NumberFormatException e) {
                    //kalau relevance bukan angka maka skip baris tersebut
                    continue;
                }
            }
        } catch (IOException e) {
            //tampilkan error kalau file gagal dibaca
            System.out.println("Error reading qrels file: " + e.getMessage());
        }
        //return semua judgment yang sudah dibaca
        return judgments;
    }
}
