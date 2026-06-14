package ir.retrieval;

import ir.preprocessing.TextPreprocessor;
import ir.retrieval.model.BIMModel;
import ir.retrieval.model.BM10Model;
import ir.retrieval.model.BM25Model;
import ir.retrieval.model.TwoPoissonModel;

public class RetrievalModelFactory {

    public RetrievalModel create(ModelType modelType, TextPreprocessor preprocessor) {
        //jika modelType adalah BIM, maka kembalikan BIMModel
        if(modelType == ModelType.BIM){
            return new BIMModel(preprocessor);
        }
        //jika modelType adalah TwoPoissonModel, maka kembalikan model twoPoission
        if(modelType == ModelType.TWO_POISSON){
            return new TwoPoissonModel(preprocessor);
        }
        //jika modelType adalah BM25, maka kembalikan model BM25
        if(modelType == ModelType.BM25){
            return new BM25Model(preprocessor);
        }
        //jika modelType adalah BM10, maka kembalikan model BM10
        if(modelType == ModelType.BM10){
            return new BM10Model(preprocessor);
        }
        //jika tidak sesuai, lempar error atau return default model
        throw new IllegalArgumentException("Unknown retrieval model: " + modelType);
    }

    public RetrievalModel createBM25(TextPreprocessor preprocessor, double k1, double b) {
        //buat BM25Model dengan parameter dari user
        return new BM25Model(preprocessor, k1, b);
    }

    public RetrievalModel createTwoPoisson(TextPreprocessor preprocessor, double k) {
        //buat TwoPoissonModel dengan parameter dari user
        return new TwoPoissonModel(preprocessor, k);
    }

    public RetrievalModel createBM10(TextPreprocessor preprocessor, double k1) {
        //buat BM10Model dengan parameter k1 dari user
        return new BM10Model(preprocessor, k1);
    }
}
