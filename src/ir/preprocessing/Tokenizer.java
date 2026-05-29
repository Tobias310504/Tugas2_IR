package ir.preprocessing;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    // method untuk memecah teks menjadi token.
    public List<String> tokenize(String text) {
        List<String> tokens = new ArrayList<>();
        //pastikan text ada isinya dan bukan null
        if(text == null){
            return tokens;
        }
        // Pisahkan teks berdasarkan spasi.
        text = text.trim();
        // Buang token kosong.
        if(text.isEmpty()){
            return tokens;
        }
        //hapus karakter selain huruf dan angka dan ubah menjadi spasi
        String cleanText = text.replaceAll("[^a-zA-Z0-9\\s]", " ");
        //memecah teks berdasarkan spasi
        String[] rawTokens = cleanText.split("\\s+");
        //loop seluruh rawTokens yang ada
        for(String token : rawTokens){
            //trim dulu token untuk memastikan tidak ada spasi lagi
            token = token.trim();
            //kalau token tidak kosong masukan ke list hasil
            if(!token.isEmpty()){
                tokens.add(token);
            }
        }
        // Return List<String>.
        return tokens;
    }
}