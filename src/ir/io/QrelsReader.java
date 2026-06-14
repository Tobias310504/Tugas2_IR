package ir.io;

import ir.evaluation.RelevanceJudgment;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QrelsReader {

    public List<RelevanceJudgment> readQrels(String filePath) {
        //inisialisasikan arraylist untuk menyimpan seluruh dokumen
        List<RelevanceJudgment> eval  = new ArrayList<RelevanceJudgment>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            //scan line per line sampai line tidak ada lgi
            while ((line = reader.readLine()) != null){
                line = line.trim();
                //kalau baris kosong di skip
                if(line.isEmpty()){
                    continue;
                }
                //format relevance judgment adalah 1 baris 1 relevance judgment (query|documentId|relevansi nya)
                String[] parts = line.split("\\|", 3);
                //kalau format relevance judgment tidak sesuai maka skip baris tersebut
                if(parts.length < 3){
                    continue;
                }
                //ambil query nya yaitu di parts index 0
                String query = parts[0].trim();
                //ambil dokumen id nya  di parts index 1
                String documentId = parts[1].trim();
                //ambil text yang relevan di parts index 2
                String relevanceText = parts[2].trim();
                try {
                    //parse relevanceText menjadi integer
                    int relevance = Integer.parseInt(relevanceText);
                    //inisialisasi relevance judgment untuk menyimpan query, id dokumen, dan relevansinya
                    RelevanceJudgment hasil = new RelevanceJudgment(query, documentId, relevance);
                    eval.add(hasil);
                } catch (NumberFormatException e) {
                    //kalau nilai relevance tidak valid maka skip baris tersebut
                    continue;
                }
            }
        }catch (IOException e){
            System.out.println("Error opening file: "+ e.getMessage());
        }
        return eval;
    }
}
