package ir.io;

import java.util.Scanner;

public class ConsoleInput {

    private Scanner scanner;

    public ConsoleInput() {
        //TODO buat Scanner untuk membaca input dari terminal
    }

    public String readQuery() {
        //TODO tampilkan pesan untuk memasukkan query
        //TODO baca query dari terminal menggunakan scanner
        //TODO trim query supaya spasi depan belakang hilang
        //TODO return query yang sudah dibaca
        return null;
    }

    public String readModelChoice() {
        //TODO tampilkan pilihan model retrieval
        //TODO tampilkan pilihan 1 untuk BIM
        //TODO tampilkan pilihan 2 untuk Two-Poisson
        //TODO tampilkan pilihan 3 untuk BM25
        //TODO tampilkan pilihan 4 untuk BM10
        //TODO baca pilihan user dari terminal
        //TODO trim pilihan user
        //TODO return pilihan user
        return null;
    }

    public int readTopK() {
        //TODO tampilkan pesan untuk memasukkan topK
        //TODO baca input topK dari terminal
        //TODO ubah input dari String menjadi int
        //TODO kalau input valid dan lebih besar dari 0 maka return nilai tersebut
        //TODO kalau input tidak valid maka gunakan nilai default
        return 0;
    }
}
