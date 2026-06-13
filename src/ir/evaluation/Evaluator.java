package ir.evaluation;

import ir.retrieval.SearchResult;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Evaluator {

    public EvaluationResult evaluate(String modelName, String queryId,
                                     List<SearchResult> rankedResults,
                                     List<RelevanceJudgment> judgments,
                                     int topK) {
        //ambil semua dokumen relevant untuk queryId ini
        Set<String> relevantDocuments = getRelevantDocuments(queryId, judgments);

        //ambil batas evaluasi berdasarkan topK
        int limit = getEvaluationLimit(rankedResults, topK);

        //hitung precision@K
        double precisionAtK = computePrecisionAtK(rankedResults, relevantDocuments, limit);

        //hitung recall@K
        double recallAtK = computeRecallAtK(rankedResults, relevantDocuments, limit);

        //hitung f1@K
        double f1AtK = computeF1(precisionAtK, recallAtK);

        //hitung average precision
        double averagePrecision = computeAveragePrecision(rankedResults, relevantDocuments, limit);

        //buat object EvaluationResult untuk menyimpan hasil evaluasi
        return new EvaluationResult(
                modelName,
                queryId,
                limit,
                precisionAtK,
                recallAtK,
                f1AtK,
                averagePrecision
        );
    }

    public List<EvaluationResult> evaluateAll(String modelName,
                                              Map<String, List<SearchResult>> resultsByQueryId,
                                              List<RelevanceJudgment> judgments,
                                              int topK) {
        //inisialisasikan list untuk menyimpan hasil evaluasi
        List<EvaluationResult> results = new ArrayList<EvaluationResult>();

        //loop semua hasil ranking berdasarkan queryId
        for (Map.Entry<String, List<SearchResult>> entry : resultsByQueryId.entrySet()) {
            //ambil queryId dari map
            String queryId = entry.getKey();

            //ambil ranking untuk queryId tersebut
            List<SearchResult> rankedResults = entry.getValue();

            //evaluasi ranking untuk satu query
            EvaluationResult result = evaluate(modelName, queryId, rankedResults, judgments, topK);

            //masukkan hasil evaluasi ke list
            results.add(result);
        }

        //return semua hasil evaluasi
        return results;
    }

    public double computeMeanAveragePrecision(List<EvaluationResult> results) {
        //kalau tidak ada hasil evaluasi maka return 0
        if (results == null || results.isEmpty()) {
            return 0.0;
        }

        //inisialisasi total average precision
        double total = 0.0;

        //loop semua hasil evaluasi
        for (EvaluationResult result : results) {
            //tambahkan average precision ke total
            total += result.getAveragePrecision();
        }

        //MAP adalah rata-rata dari semua average precision
        return total / results.size();
    }

    private Set<String> getRelevantDocuments(String queryId, List<RelevanceJudgment> judgments) {
        //gunakan Set agar documentId tidak duplikat
        Set<String> relevantDocuments = new HashSet<String>();

        //kalau judgments null maka return set kosong
        if (judgments == null) {
            return relevantDocuments;
        }

        //loop semua relevance judgment
        for (RelevanceJudgment judgment : judgments) {
            //ambil judgment yang query id sama dan dokumennya relevant
            if (judgment.getQueryId().equals(queryId) && judgment.isRelevant()) {
                //masukkan documentId ke set
                relevantDocuments.add(judgment.getDocumentId());
            }
        }

        //return semua dokumen relevant
        return relevantDocuments;
    }

    private int getEvaluationLimit(List<SearchResult> rankedResults, int topK) {
        //kalau ranking null atau kosong maka return 0
        if (rankedResults == null || rankedResults.isEmpty()) {
            return 0;
        }

        //kalau topK tidak valid atau lebih besar dari ranking, pakai semua ranking
        if (topK <= 0 || topK > rankedResults.size()) {
            return rankedResults.size();
        }

        //return topK sebagai batas evaluasi
        return topK;
    }

    private double computePrecisionAtK(List<SearchResult> rankedResults,
                                       Set<String> relevantDocuments,
                                       int limit) {
        //kalau tidak ada dokumen yang dievaluasi maka precision 0
        if (limit == 0) {
            return 0.0;
        }

        //hitung dokumen relevant yang berhasil di retrieve
        int relevantRetrieved = countRelevantRetrieved(rankedResults, relevantDocuments, limit);

        //precision@K = relevant retrieved / retrieved
        return (double) relevantRetrieved / limit;
    }

    private double computeRecallAtK(List<SearchResult> rankedResults,
                                    Set<String> relevantDocuments,
                                    int limit) {
        //kalau tidak ada dokumen relevant maka recall 0
        if (relevantDocuments.isEmpty()) {
            return 0.0;
        }

        //hitung dokumen relevant yang berhasil di retrieve
        int relevantRetrieved = countRelevantRetrieved(rankedResults, relevantDocuments, limit);

        //recall@K = relevant retrieved / total relevant
        return (double) relevantRetrieved / relevantDocuments.size();
    }

    private double computeF1(double precision, double recall) {
        //kalau precision dan recall 0 maka return 0
        if (precision == 0.0 && recall == 0.0) {
            return 0.0;
        }

        //hitung f1 menggunakan harmonic mean
        return (2 * precision * recall) / (precision + recall);
    }

    private double computeAveragePrecision(List<SearchResult> rankedResults,
                                           Set<String> relevantDocuments,
                                           int limit) {
        //kalau tidak ada dokumen relevant atau ranking kosong maka return 0
        if (relevantDocuments.isEmpty() || limit == 0) {
            return 0.0;
        }

        //hitung jumlah dokumen relevant yang sudah ditemukan
        int relevantSeen = 0;

        //simpan total precision di setiap posisi dokumen relevant
        double precisionSum = 0.0;

        //loop ranking sampai batas topK
        for (int i = 0; i < limit; i++) {
            //ambil hasil ranking pada index ke-i
            SearchResult result = rankedResults.get(i);

            //cek apakah dokumen ini relevant
            if (relevantDocuments.contains(result.getDocumentId())) {
                //tambahkan jumlah dokumen relevant yang sudah terlihat
                relevantSeen++;

                //rank dimulai dari 1
                int rank = i + 1;

                //tambahkan precision pada posisi dokumen relevant ini
                precisionSum += (double) relevantSeen / rank;
            }
        }

        //average precision = total precision / total dokumen relevant
        return precisionSum / relevantDocuments.size();
    }

    private int countRelevantRetrieved(List<SearchResult> rankedResults,
                                       Set<String> relevantDocuments,
                                       int limit) {
        //inisialisasi counter dokumen relevant
        int count = 0;

        //loop ranking sampai batas topK
        for (int i = 0; i < limit; i++) {
            //ambil hasil ranking pada index ke-i
            SearchResult result = rankedResults.get(i);

            //kalau dokumen relevant maka counter ditambah
            if (relevantDocuments.contains(result.getDocumentId())) {
                count++;
            }
        }

        //return jumlah dokumen relevant yang berhasil di retrieve
        return count;
    }

    // public double computeElevenPointAverage(String queryId, List<SearchResult> rankedResults, List<RelevanceJudgment> judgments) {
    //     //Ambil dokumen relevant untuk query ini
    //     Set<String> relevantDocuments = getRelevantDocuments(queryId, judgments);

    //     //Kalau tidak ada dokumen relevant, nilai 0
    //     if (relevantDocuments.isEmpty() || rankedResults == null || rankedResults.isEmpty()) {
    //         return 0.0;
    //     }

    //     //Titik recall: 0.0, 0.1, 0.2, ..., 1.0
    //     double[] recallLevels = {0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};

    //     //Menyimpan precision maksimum untuk setiap recall level
    //     double[] maxPrecisionAtRecall = new double[recallLevels.length];

    //     int relevantSeen = 0;

    //     //Loop ranking dokumen
    //     for (int i = 0; i < rankedResults.size(); i++) {
    //         SearchResult result = rankedResults.get(i);

    //         //Kalau dokumen pada posisi ini relevant
    //         if (relevantDocuments.contains(result.getDocumentId())) {
    //             relevantSeen++;

    //             int rank = i + 1;

    //             //Recall pada posisi ini
    //             double recall = (double) relevantSeen / relevantDocuments.size();

    //             //Precision pada posisi ini
    //             double precision = (double) relevantSeen / rank;

    //             //Update precision maksimum untuk setiap recall level yang terpenuhi
    //             for (int j = 0; j < recallLevels.length; j++) {
    //                 if (recall >= recallLevels[j] && precision > maxPrecisionAtRecall[j]) {
    //                     maxPrecisionAtRecall[j] = precision;
    //                 }
    //             }
    //         }
    //     }

    //     //Rata-rata dari 11 titik recall
    //     double total = 0.0;
    //     for (int i = 0; i < maxPrecisionAtRecall.length; i++) {
    //         total += maxPrecisionAtRecall[i];
    //     }

    //     return total / 11.0;
    // }
}
