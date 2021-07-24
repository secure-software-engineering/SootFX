package api;

public class FeatureDescription {

    private String name;
    private String desc;

    public FeatureDescription(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }
    public String getDesc() {
        return desc;
    }
}
