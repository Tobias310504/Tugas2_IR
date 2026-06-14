package ir.io;

import java.util.Scanner;

public class ConsoleInput {

    private Scanner scanner;

    public ConsoleInput() {
        this.scanner = new Scanner(System.in);
    }

    public String readRunMode() {
        //pilihan mode menjalankan program
        System.out.println("Pilih mode program:");
        //pilihan 1 untuk input query manual dari terminal
        System.out.println("1. Input query sendiri");
        //pilihan 2 untuk membaca query dari file txt
        System.out.println("2. Input query dari file queries.txt");
        //baca pilihan user
        System.out.print("Pilihan: ");
        String pilihan = scanner.nextLine();
        //trim pilihan user
        pilihan = pilihan.trim();
        //kembalikan pilihan user
        return pilihan;
    }

    public String readQuery() {
        System.out.println("Masukan kata yang ingin dicari");
        //baca query dari terminal menggunakan scanner
        String query = scanner.nextLine();
        //trim query untuk menghilangkan spasi
        query = query.trim();
        //return query 
        return query;
    }

    public String readModelChoice() {
        //pilihan model retrieval
        System.out.println("Pilih model retrieval (Menulis angka pilihan) :");
        //pilihan 1 untuk BIM
        System.out.println("1. BIM");
        //pilihan 2 untuk Two-poisson
        System.out.println("2. Two-Poisson");
        //pilihan 3 untuk BM25
        System.out.println("3. BM25");
        //pilihan 4 untuk BM10
        System.out.println("4. BM10");
        //baca plihan user
        System.out.print("Pilihan: ");
        String pilihan = scanner.nextLine();
        //trim pilihan user
        pilihan = pilihan.trim();
        //kembalikan pilihan user
        return pilihan;
    }

    public int readTopK() {
        //tampilkan pesan untuk memasukkan topK
        System.out.println("TopK: ");
        //baca input topK 
        String input = scanner.nextLine();
        try {
            //ubah input dari String menjadi int
            int topK = Integer.parseInt(input);
            //kalau input valid dan lebih besar dari 0 maka return nilai tersebut
            if(topK > 0){
                return topK;
            }
        } catch (NumberFormatException e) {
            System.out.println("input topK tidak valid, memakai default 10");
        }
        //kalau input tidak valid maka gunakan nilai default = 10
        return 10;
    }

    public double readDoubleParameter(String parameterName, double defaultValue) {
        //tampilkan pesan untuk memasukkan parameter
        System.out.print(parameterName + " (kosongkan untuk default " + defaultValue + "): ");
        //baca input parameter
        String input = scanner.nextLine();
        //trim input parameter
        input = input.trim();
        //kalau input kosong maka pakai nilai default
        if(input.isEmpty()){
            return defaultValue;
        }
        try {
            //ubah input dari String menjadi double
            double value = Double.parseDouble(input);
            //kalau input valid maka return nilai tersebut
            return value;
        } catch (NumberFormatException e) {
            System.out.println("input " + parameterName + " tidak valid, memakai default " + defaultValue);
        }
        //kalau input tidak valid maka gunakan nilai default
        return defaultValue;
    }

    public String readEvaluationMenuChoice() {
        //pilihan setelah evaluasi selesai
        System.out.println("Pilih menu selanjutnya:");
        //pilihan 1 untuk mencoba evaluasi model lain
        System.out.println("1. Evaluasi model lain");
        //pilihan 2 untuk mengakhiri program
        System.out.println("2. Selesai");
        //baca pilihan user
        System.out.print("Pilihan: ");
        String pilihan = scanner.nextLine();
        //trim pilihan user
        pilihan = pilihan.trim();
        //kembalikan pilihan user
        return pilihan;
    }
}
