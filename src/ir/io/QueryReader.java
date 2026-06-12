package ir.io;

import ir.query.Query;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QueryReader {

    public List<Query> readQueries(String filePath) {
        //inisialisasikan list untuk menyimpan semua query
        List<Query> queries = new ArrayList<Query>();
        //buka file queries.txt
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
                //format query adalah queryId|queryText
                String[] parts = line.split("\\|", 2);
                //kalau format query tidak sesuai maka skip baris tersebut
                if (parts.length < 2) {
                    continue;
                }
                //ambil query id dari parts index 0
                String queryId = parts[0].trim();
                //ambil query text dari parts index 1
                String queryText = parts[1].trim();
                //kalau queryId atau queryText kosong maka skip
                if (queryId.isEmpty() || queryText.isEmpty()) {
                    continue;
                }
                //buat object Query untuk menyimpan query id dan text
                Query query = new Query(queryId, queryText);
                //masukkan query ke list
                queries.add(query);
            }
        } catch (IOException e) {
            //tampilkan error kalau file gagal dibaca
            System.out.println("Error reading queries file: " + e.getMessage());
        }
        //return semua query yang sudah dibaca
        return queries;
    }
}
