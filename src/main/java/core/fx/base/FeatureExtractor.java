package core.fx.base;


public interface FeatureExtractor<V,T> {

    default String getName(){
        return this.getClass().getSimpleName();
    }

    default String getName(String value){
        return String.format("%s(\"%s\")", getName(), value);
    }

    Feature<V> extract(T target);

}
