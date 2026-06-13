package ir.evaluation;

public class RelevanceJudgment {

    private String queryId;
    private String documentId;
    private int relevance;
    //konsturktor
    public RelevanceJudgment(String queryId, String documentId, int relevance) {
        //simpan queryId ke atribut queryId
        this.queryId = queryId;
        //simpan documentId ke atribut documentId
        this.documentId = documentId;
        //simpan relevance ke atribut relevance
        this.relevance = relevance;
    }

    public String getQueryId() {
        return queryId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public int getRelevance() {
        return relevance;
    }

    public boolean isRelevant() {
        //cek apakah relevance lebih besar dari 0
        if(relevance > 0){
            //kalau dokumen relevan kembalikan true
            return true;
        }
        //kalau tidak relevan kembalikan false
        return false;
    }
}
