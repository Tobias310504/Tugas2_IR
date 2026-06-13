package ir.io;

import ir.query.Query;
import java.util.List;

public class QueryReader {

    public List<Query> readQueries(String filePath) {
        //TODO buat list untuk menyimpan semua query
        //TODO buka file queries.txt berdasarkan filePath
        //TODO baca file baris per baris
        //TODO trim setiap baris supaya spasi depan belakang hilang
        //TODO kalau baris kosong maka lanjut ke baris berikutnya
        //TODO pecah baris dengan tanda | karena formatnya queryId|queryText
        //TODO kalau format tidak sesuai maka baris tersebut dilewati
        //TODO ambil queryId dari bagian pertama
        //TODO ambil queryText dari bagian kedua
        //TODO kalau queryId atau queryText kosong maka baris tersebut dilewati
        //TODO buat object Query dari queryId dan queryText
        //TODO masukkan object Query ke list
        //TODO tangani error kalau file gagal dibaca
        //TODO return list query yang sudah berhasil dibaca
        return null;
    }
}
