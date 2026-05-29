package ir;

import ir.app.IRApplication;

public class Main {

    public static void main(String[] args) {
        // Membuat object IRApplication.
        IRApplication app = new IRApplication();
        app.run();
        // Method ini hanya menjadi titik masuk program.
        // Semua alur utama dijalankan di IRApplication.
    }
}