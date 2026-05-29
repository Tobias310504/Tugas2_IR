package ir.document;

public class Document {

    private String id;
    private String title;
    private String content;
    //konstruktornya
    public Document(String id, String title, String content) {
        // Menyimpan ID dokumen dan isi dokumen.
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }
}