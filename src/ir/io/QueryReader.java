package ir.io;

import ir.query.Query;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QueryReader {

    public List<Query> readQueries(String filePath) {
     //inisialisasikan arraylist untuk menyimpan seluruh dokumen
        List<Query> queries  = new ArrayList<Query>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            //scan line per line sampai line tidak ada lgi
            while ((line = reader.readLine()) != null){
                line = line.trim();
                //kalau baris kosong di skip
                if(line.isEmpty()){
                    continue;
                }
                //format query adalah 1 baris 1 query (QueryId|querynya)
                String[] parts = line.split("\\|", 2);
                //kalau format query tidak sesuai maka skip baris tersebut
                if(parts.length < 2){
                    continue;
                }
                //ambil query id nya yaitu di parts index 0
                String QueryId = parts[0].trim();
                //ambil isi query nya di parts index 1
                String isiQuery = parts[1].trim();
                //inisialisasi query untuk menyimpan query id dan query nya
                Query query = new Query(QueryId, isiQuery);
                queries.add(query);
            }
        }catch (IOException e){
            System.out.println("Error opening file: "+ e.getMessage());
        }
        return queries;
    }
}
