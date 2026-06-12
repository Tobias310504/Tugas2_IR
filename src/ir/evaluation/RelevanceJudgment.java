package ir.evaluation;

public class RelevanceJudgment {

    private String queryId;
    private String documentId;
    private int relevance;

    public RelevanceJudgment(String queryId, String documentId, int relevance) {
        //simpan query id dari qrels
        this.queryId = queryId;

        //simpan document id dari qrels
        this.documentId = documentId;

        //simpan nilai relevance
        this.relevance = relevance;
    }

    public String getQueryId() {
        //method untuk mengembalikan query id
        return queryId;
    }

    public String getDocumentId() {
        //method untuk mengembalikan document id
        return documentId;
    }

    public int getRelevance() {
        //method untuk mengembalikan nilai relevance
        return relevance;
    }

    public boolean isRelevant() {
        //dokumen dianggap relevant jika nilainya lebih besar dari 0
        return relevance > 0;
    }
}
