package core.fx.base;


public class Feature<V> implements Comparable<Feature<V>>{

    private String name;
    private V value;

    public Feature(String name, V value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public V getValue() {
        return value;
    }

    @Override
    public int compareTo(Feature<V> other) {
        return name.compareToIgnoreCase(other.getName());
    }
}
