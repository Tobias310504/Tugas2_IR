package ir.preprocessing;

import java.util.List;
import java.util.Set;

public class StopwordRemover {

    private Set<String> stopwords;

    public StopwordRemover() {
        // Inisialisasi daftar stopword.
        //
        // Contoh stopword:
        // the, is, are, a, an, of, to, in, on, for, and
    }

    public List<String> remove(List<String> tokens) {
        // Menghapus token yang termasuk stopword.
        //
        // STEP 1:
        // Siapkan List<String> hasil.
        //
        // STEP 2:
        // Loop setiap token.
        //
        // STEP 3:
        // Jika token ada di stopwords, skip.
        //
        // STEP 4:
        // Jika bukan stopword, masukkan ke list hasil.
        //
        // STEP 5:
        // Return hasil.
        return null;
    }
}