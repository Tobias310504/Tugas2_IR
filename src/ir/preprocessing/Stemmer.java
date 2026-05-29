package ir.preprocessing;

import java.util.List;
import java.util.ArrayList;

public class Stemmer {
    //aturan-aturan apa saya yang harus di stemmer
    public String stem(String token) {
        //kalau token null return string kosong
        if(token == null){
            return "";
        }
        //trim dulu tokennya
        token = token.trim();
        //cek apakah panjang token 3 atau lebih besar
        if(token.length() <= 3){
            //langsung return tokennya
            return token;
        }
        //cek apakah token berakhiran "ies"
        if(token.endsWith("ies") && token.length() > 4){
            return token.substring(0, token.length() - 3) + "y";
        }
        // cek apakah token berakhiran "ing"
        if(token.endsWith("ing") && token.length() > 5){
            return token.substring(0, token.length()-3);
        }
        // Cek apakah token berakhiran "ed"
        if(token.endsWith("ed") && token.length() > 4){
            return token.substring(0, token.length()-2);
        }
        // Cek apakah token berakhiran "s"
        if(token.endsWith("s") && token.length() > 4){
            return token.substring(0, token.length()-1);
        }
        // Return token hasil stemming
        return token;
    }
    // mehtod untuk melakukan stemming untuk semua token
    public List<String> stemAll(List<String> tokens) {
        // inisialisasikan list hasil
        List<String> stemmedTokens = new ArrayList<>();
        //pastikan token tidak null
        if(tokens == null || tokens.isEmpty()){
            return stemmedTokens;
        }
        // loop semua token.
        for (String token : tokens) {
            // panggil method stem(token) untuk mengstem token yang ada sesuai aturan
            String stem = stem(token);
            //kalau list stemmedTokens tidak kosong
            if(!stem.isEmpty()){
                //masukkan token yang sudah di stemm ke arraylist hasil
                stemmedTokens.add(stem);
            }
        }
        // Return list hasil.
        return stemmedTokens;
    }
}