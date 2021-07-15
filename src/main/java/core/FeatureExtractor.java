package core;

public interface FeatureExtractor<F,T> {

    F extract(T target);

}
