package ir.preprocessing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StopwordRemover {

    private Set<String> stopwords;

    public StopwordRemover() {
        // inisialisasi daftar stopword.
       this.stopwords = new HashSet<String>(Arrays.asList(
               "a", "an", "the", "is", "are", "was", "were", "be", "been", "being", "am", "do", "does", "did", "has", "have", "had", "of", "to", "in", "on", "for", "from", "by", "with", "at", "into", "through", "during", "before", "after", "above", "below", "over", "under", "between", "among", "and", "or", "but", "because", "so", "if", "while", "i", "you", "he", "she", "it", "we", "they", "me", "him", "her", "us", "them", "my", "your", "his", "its", "our", "their", "this", "that", "these", "those", "all", "any", "both", "each", "few", "more", "most", "other", "some", "such", "no", "not", "only", "own", "same", "than", "too", "very", "can", "will", "should", "now", "then", "there", "here", "using", "used", "use", "also", "often", "commonly"
       ));
    }
    // method untuk menghapus token yang termasuk stopword.
    public List<String> remove(List<String> tokens) {
        // inisialisasikan List<String> hasil untuk menyimpan semua token yang ada
        List<String> filterToken = new ArrayList<String>();
        //cek apakah token null atau tidak atau apakah token kosong
        if(tokens == null||tokens.isEmpty()){
            return filterToken;
        }
        // loop setiap token.
        for (String token : tokens) {
            //kalau tokens null langsung skip
            if(tokens == null){
                continue;
            }
            //trim dulu tokennya untuk menghilangkan spasi
            token = token.trim();
            //kalau token tidak ada di list, maka skip
            if (token.isEmpty()) {
                continue;
            }
            // jika bukan merupakan stopword, masukkan ke list hasil.
            if (!stopwords.contains(token)) {
                filterToken.add(token);
            }
        }
        // return hasil
        return filterToken;
    }
}