package ir.evaluation;

public class EvaluationResult {

    private String modelName;
    private String queryId;
    private int topK;
    private double precisionAtK;
    private double recallAtK;
    private double f1AtK;
    private double averagePrecision;

    public EvaluationResult(String modelName, String queryId, int topK, double precisionAtK, double recallAtK, double f1AtK, double averagePrecision) {
        this.modelName = modelName;
        this.queryId = queryId;
        this.topK = topK;
        this.precisionAtK = precisionAtK;
        this.recallAtK = recallAtK;
        this.f1AtK = f1AtK;
        this.averagePrecision = averagePrecision;
    }
    
    public String getModelName() {
        return modelName;
    }

    public String getQueryId() {
        return queryId;
    }

    public int getTopK() {
        return topK;
    }

    public double getPrecisionAtK() {
        return precisionAtK;
    }

    public double getRecallAtK() {
        return recallAtK;
    }

    public double getF1AtK() {
        return f1AtK;
    }

    public double getAveragePrecision() {
        return averagePrecision;
    }
}
