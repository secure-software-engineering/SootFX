package core.fx.base;

public class Feature<T> implements Comparable<Feature<T>>{

    private String name;
    private T value;

    public Feature(String name, T value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    @Override
    public int compareTo(Feature<T> other) {
        return name.compareToIgnoreCase(other.getName());
    }
}
