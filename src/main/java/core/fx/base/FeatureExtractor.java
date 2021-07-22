package core.fx.base;


public interface FeatureExtractor<V,T> {

    Feature<V> extract(T target);

}
