package ir.io;

import ir.evaluation.RelevanceJudgment;
import java.util.List;

public class QrelsReader {

    public List<RelevanceJudgment> readQrels(String filePath) {
        //TODO buat list untuk menyimpan semua relevance judgment
        //TODO buka file qrels.txt berdasarkan filePath
        //TODO baca file baris per baris
        //TODO trim setiap baris supaya spasi depan belakang hilang
        //TODO kalau baris kosong maka lanjut ke baris berikutnya
        //TODO pecah baris dengan tanda | karena formatnya queryId|documentId|relevance
        //TODO kalau format tidak sesuai maka baris tersebut dilewati
        //TODO ambil queryId dari bagian pertama
        //TODO ambil documentId dari bagian kedua
        //TODO ambil relevance dari bagian ketiga
        //TODO kalau ada data yang kosong maka baris tersebut dilewati
        //TODO ubah relevance dari String menjadi int
        //TODO kalau relevance bukan angka maka baris tersebut dilewati
        //TODO buat object RelevanceJudgment dari queryId, documentId, dan relevance
        //TODO masukkan object RelevanceJudgment ke list
        //TODO tangani error kalau file gagal dibaca
        //TODO return list relevance judgment yang sudah berhasil dibaca
        return null;
    }
}
