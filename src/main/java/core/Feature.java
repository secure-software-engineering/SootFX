package core;

public class Feature<T> {

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
}
