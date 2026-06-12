package ir.io;

import ir.document.Document;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DocumentReader {
    public List<Document> readDocuments(String fileName) {
        //inisialisasikan arraylist untuk menyimpan seluruh dokumen
        List<Document> documents  = new ArrayList<Document>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line;
            //scan line per line sampai line tidak memiliki artikel lgi
            while ((line = reader.readLine()) != null){
                line = line.trim();
                //kalau baris kosong di skip
                if(line.isEmpty()){
                    continue;
                }
                //format document adalah 1 baris 1 artikel (docId|Judul|contentnya)
                String[] parts = line.split("\\|", 3);
                //kalau format dokumen tidak sesuai maka skip baris tersebut
                if(parts.length < 3){
                    continue;
                }
                //ambil doc id nya yaitu di parts index 0
                String id = parts[0].trim();
                //ambil judulnya di parts index 1
                String title = parts[1].trim();
                //ambil contentnya di parts index 2
                String content = parts[2].trim();
                //inisialisasi dokumen untuk menyimpan docId, title, dan contentnya
                Document document = new Document(id, title, content);
                documents.add(document);
            }
        }catch (IOException e){
            System.out.println("Error opening file: "+ e.getMessage());
        }
        return documents;
    }
}