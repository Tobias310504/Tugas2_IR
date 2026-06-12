package ir.query;

public class Query {

    private String id;
    private String text;
    //konstruktornya 
    public Query(String id, String text) {
        this.id = id;
        this.text = text;
    }
    //buat getter dan setternya 
    public String getId() {
        return this.id;
    }
    public String getText() {
        return this.text;
    }
}