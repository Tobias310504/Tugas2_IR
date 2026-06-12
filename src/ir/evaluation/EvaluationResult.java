package ir.evaluation;

public class EvaluationResult {

    private String modelName;
    private String queryId;
    private int topK;
    private double precisionAtK;
    private double recallAtK;
    private double f1AtK;
    private double averagePrecision;

    public EvaluationResult(String modelName, String queryId, int topK,
                            double precisionAtK, double recallAtK,
                            double f1AtK, double averagePrecision) {
        //simpan nama model
        this.modelName = modelName;

        //simpan query id
        this.queryId = queryId;

        //simpan nilai topK
        this.topK = topK;

        //simpan nilai precision@K
        this.precisionAtK = precisionAtK;

        //simpan nilai recall@K
        this.recallAtK = recallAtK;

        //simpan nilai f1@K
        this.f1AtK = f1AtK;

        //simpan nilai average precision
        this.averagePrecision = averagePrecision;
    }

    public String getModelName() {
        //method untuk mengembalikan nama model
        return modelName;
    }

    public String getQueryId() {
        //method untuk mengembalikan query id
        return queryId;
    }

    public int getTopK() {
        //method untuk mengembalikan nilai topK
        return topK;
    }

    public double getPrecisionAtK() {
        //method untuk mengembalikan precision@K
        return precisionAtK;
    }

    public double getRecallAtK() {
        //method untuk mengembalikan recall@K
        return recallAtK;
    }

    public double getF1AtK() {
        //method untuk mengembalikan f1@K
        return f1AtK;
    }

    public double getAveragePrecision() {
        //method untuk mengembalikan average precision
        return averagePrecision;
    }
}
