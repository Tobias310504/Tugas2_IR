package ir.io;

import java.util.Scanner;

public class ConsoleInput {

    private Scanner scanner;

    public ConsoleInput() {
        //buat Scanner untuk membaca input dari terminal
        this.scanner = new Scanner(System.in);
    }

    public String readQuery() {
        //tampilkan pesan untuk memasukkan query
        System.out.print("Enter query: ");
        //baca query dari terminal
        String query = scanner.nextLine();
        //return query yang sudah di trim
        return query.trim();
    }

    public String readModelChoice() {
        //tampilkan pilihan model
        System.out.println("Choose retrieval model:");
        //pilihan pertama adalah BIM
        System.out.println("1. BIM");
        //pilihan kedua adalah Two-Poisson
        System.out.println("2. Two-Poisson");
        //pilihan ketiga adalah BM25
        System.out.println("3. BM25");
        //pilihan keempat adalah BM10
        System.out.println("4. BM10");
        //minta user memilih model
        System.out.print("Choice: ");
        //baca pilihan user
        String choice = scanner.nextLine();
        //return pilihan yang sudah di trim
        return choice.trim();
    }

    public int readTopK() {
        //tampilkan pesan untuk memasukkan topK
        System.out.print("Top K: ");
        //baca input user
        String input = scanner.nextLine();
        try {
            //ubah input dari String menjadi int
            int topK = Integer.parseInt(input.trim());
            //kalau topK valid maka return topK
            if (topK > 0) {
                return topK;
            }
        } catch (NumberFormatException e) {
            //kalau input bukan angka, gunakan default
            System.out.println("Menggunakan K = 10 default....");
        }
        //return default topK
        return 10;
    }
}
