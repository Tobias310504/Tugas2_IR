package ir.evaluation;

import ir.retrieval.SearchResult;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Evaluator {

    public EvaluationResult evaluate(String modelName, String queryId, List<SearchResult> rankedResults, List<RelevanceJudgment> judgments, int topK) {
        //ambil semua documentId yang relevant untuk queryId ini dari qrels
        Set<String> relevantDocuments = getRelevantDocuments(queryId, judgments);
        //tentukan batas evaluasi berdasarkan topK dan jumlah rankedResults
        int limit = getEvaluationLimit(rankedResults, topK);
        //hitung precision@K
        double precision = computePrecisionAtK(rankedResults, relevantDocuments, limit);
        //hitung recall@K
        double recall = computeRecallAtK(rankedResults, relevantDocuments, limit);
        //hitung f1@K dari precision dan recall
        double f1 = computeF1(precision, recall);
        //hitung average precision untuk query ini
        double avgPrecision = computeAveragePrecision(rankedResults, relevantDocuments, limit);
        //buat object EvaluationResult dari semua nilai evaluasi
        EvaluationResult hasil = new EvaluationResult(modelName, queryId, limit, precision, recall, f1, avgPrecision);
        //kembalikan object EvaluationResult
        return hasil;
    }

    public List<EvaluationResult> evaluateAll(String modelName, Map<String, List<SearchResult>> resultsByQueryId, List<RelevanceJudgment> judgments, int topK) {
        //buat list untuk menyimpan hasil evaluasi semua query
        List<EvaluationResult> queries = new ArrayList<EvaluationResult>(); 
        //loop setiap entry pada resultsByQueryId
        for(Map.Entry<String, List<SearchResult>> eval : resultsByQueryId.entrySet()){
            //ambil queryId dari key map
            String queryId = eval.getKey();
            //ambil rankedResults dari value map
            List<SearchResult> rankedResults = eval.getValue();
            //panggil method evaluate untuk setiap query
            EvaluationResult result = evaluate(modelName, queryId, rankedResults, judgments, topK);
            //masukan hasil evaluasi ke list
            queries.add(result);
        }
        //kembalikan list hasil evaluasi
        return queries;
    }

    public double computeMeanAveragePrecision(List<EvaluationResult> results) {
        //kalau results null atau kosong maka return 0
        if(results == null || results.isEmpty()){
            return 0;
        }
        //inisialisasikan varriabel untuk menyimpan total avg nya awal" 0.0 karna tipe datanya double
        double total = 0.0;
        //jumlahkan semua average precision dari setiap EvaluationResult
        for (EvaluationResult result : results) {
            total += result.getAveragePrecision();
        }
        //bagi total average precision dengan jumlah query
        double res = total / results.size();
        //return nilai total averagenya nya
        return res;
    }

    private Set<String> getRelevantDocuments(String queryId, List<RelevanceJudgment> judgements) {
        //buat Set untuk menyimpan documentId relevant agar tidak duplikat
        Set<String> relevantDocuments = new HashSet<String>();
        //kalau judgments null maka return Set kosong
        if(judgements == null){
            return relevantDocuments;
        }
        //loop semua RelevanceJudgment
        for(RelevanceJudgment relevan : judgements){
            //cek apakah queryId judgment sama dengan queryId parameter
            if(relevan.getQueryId().equals(queryId)){
                //cek apakah judgment tersebut relevant
                if(relevan.isRelevant()){
                    //kalau relevant maka masukkan documentId ke Set
                    relevantDocuments.add(relevan.getDocumentId());
                }
            }
        }
        //return Set documentId relevant
        return relevantDocuments;
    }

    private int getEvaluationLimit(List<SearchResult> rankedResults, int topK) {
        //kalau rankedResults null atau kosong maka return 0
        if(rankedResults == null || rankedResults.isEmpty()){
            return 0;
        }
        //kalau topK tidak valid maka pakai jumlah rankedResults
        if(topK <= 0){
            return rankedResults.size();
        }
        //kalau topK lebih besar dari jumlah rankedResults maka pakai jumlah rankedResults
        if(topK > rankedResults.size()){
            return rankedResults.size();
        }
        //return batas evaluasi
        return topK;
    }

    private double computePrecisionAtK(List<SearchResult> rankedResults, Set<String> relevantDocuments, int limit) {
        //kalau limit 0 maka return 0
        if(limit == 0){
            return 0.0;
        }
        //panggil method untuk menghitung jumlah dokumen yang relevant pada k
        int count = countRelevantRetrieved(rankedResults, relevantDocuments, limit);
        //hitung precision@K = relevant retrieved / jumlah dokumen yang diambil
        double precision = (double) count / limit;
        //kembalikan hasil precisionnya
        return precision;
    }

    private double computeRecallAtK(List<SearchResult> rankedResults, Set<String> relevantDocuments, int limit) {
        //kalau tidak ada dokumen relevant maka return 0
        if(relevantDocuments == null || relevantDocuments.isEmpty() ){
            return 0;
        }
        //panggil method untuk menghitung jumlah dokumen yang relevant pada k
        int count = countRelevantRetrieved(rankedResults, relevantDocuments, limit);
        //hitung recall@K = relevant retrieved / total dokumen relevant pada qrels
        double recall = (double) count / relevantDocuments.size();
        //kembalikan hasil recallnya
        return recall;
    }

    private double computeF1(double precision, double recall) {
        //kalau precision dan recall sama-sama 0 maka kembalikan 0
        if(precision == 0 && recall == 0){
            return 0.0;
        }
        //hitung f1 = 2 * precision * recall / (precision + recall)
        double f1 = 2 * precision * recall / (precision + recall);
        //return kembalikan hasil f1 nya
        return f1;
    }

    private double computeAveragePrecision(List<SearchResult> rankedResults, Set<String> relevantDocuments, int limit) {
        //kalau tidak ada dokumen relevant atau limit 0 maka return 0
        if(relevantDocuments == null || relevantDocuments.isEmpty() || limit == 0){
            return 0;
        }
        //buatlah variable untuk menyimpan counter dokumen relevant yang sudah ditemukan
        int relevantSeen = 0;
        //buat varriable untuk menyimpan total precision untuk posisi dokumen relevant
        double precisionTotal = 0.0;
        //loop ranking dari posisi 0 sampai limit
        for(int i = 0 ; i < limit ; i++){
            //ambil dokumen saat ini
            SearchResult res = rankedResults.get(i);
            //ambil dokumen idnya 
            String documentId = res.getDocumentId();
            //kalau relevant maka tambah counter relevant yang sudah ditemukan
            if(relevantDocuments.contains(documentId)){
                relevantSeen++;
                //hitung precision pada rank tersebut
                int rank = i+1;
                //tambahkan precision rank tersebut ke total precision        
                precisionTotal += (double) relevantSeen / rank;
            }
        }
        //hitung average precision = total precision / total dokumen relevant
        double avgPrecision = precisionTotal /relevantDocuments.size();
        //kembalikan hasil perhitungan average precision
        return avgPrecision;
    }

    private int countRelevantRetrieved(List<SearchResult> rankedResults, Set<String> relevantDocuments, int limit) {
        //siapkan counter dokumen relevant
        int relevantSeen = 0;
        //loop ranking sampai batas limit
        for(int i = 0 ; i < limit ; i++){
            //ambil document saat ini
            SearchResult res = rankedResults.get(i);
            //ambil dokumen idnya
            String documentId = res.getDocumentId();
            //kalau documentId ada di relevantDocuments maka counter ditambah
            if(relevantDocuments.contains(documentId)){
                relevantSeen++;
            }
        }
        //return jumlah dokumen relevant yang berhasil ditemukan
        return relevantSeen;
    }

    public double computeElevenPointAverage(String queryId, List<SearchResult> rankedResults, List<RelevanceJudgment> judgments) {
        //Ambil dokumen relevant untuk query ini
        Set<String> relevantDocuments = getRelevantDocuments(queryId, judgments);

        //Kalau tidak ada dokumen relevant, nilai 0
        if (relevantDocuments.isEmpty() || rankedResults == null || rankedResults.isEmpty()) {
            return 0.0;
        }

        //Titik recall: 0.0, 0.1, 0.2, ..., 1.0
        double[] recallLevels = {0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};

        //Menyimpan precision maksimum untuk setiap recall level
        double[] maxPrecisionAtRecall = new double[recallLevels.length];

        int relevantSeen = 0;

        //Loop ranking dokumen
        for (int i = 0; i < rankedResults.size(); i++) {
            SearchResult result = rankedResults.get(i);

            //Kalau dokumen pada posisi ini relevant
            if (relevantDocuments.contains(result.getDocumentId())) {
                relevantSeen++;

                int rank = i + 1;

                //Recall pada posisi ini
                double recall = (double) relevantSeen / relevantDocuments.size();

                //Precision pada posisi ini
                double precision = (double) relevantSeen / rank;

                //Update precision maksimum untuk setiap recall level yang terpenuhi
                for (int j = 0; j < recallLevels.length; j++) {
                    if (recall >= recallLevels[j] && precision > maxPrecisionAtRecall[j]) {
                        maxPrecisionAtRecall[j] = precision;
                    }
                }
            }
        }

        //Rata-rata dari 11 titik recall
        double total = 0.0;
        for (int i = 0; i < maxPrecisionAtRecall.length; i++) {
            total += maxPrecisionAtRecall[i];
        }

        return total / 11.0;
    }
}
