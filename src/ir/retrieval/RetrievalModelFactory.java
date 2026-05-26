package ir.retrieval;

import ir.preprocessing.TextPreprocessor;

public class RetrievalModelFactory {

    public RetrievalModel create(ModelType modelType, TextPreprocessor preprocessor) {
        // Membuat object model berdasarkan pilihan user.
        //
        // STEP 1:
        // Jika modelType == BIM, return BIMModel.
        //
        // STEP 2:
        // Jika modelType == TWO_POISSON, return TwoPoissonModel.
        //
        // STEP 3:
        // Jika modelType == BM25, return BM25Model.
        //
        // STEP 4:
        // Jika modelType == BM10, return BM10Model.
        //
        // STEP 5:
        // Jika tidak sesuai, lempar error atau return default model.
        return null;
    }
}