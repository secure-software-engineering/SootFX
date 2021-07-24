package api;

import java.util.List;

public class FeatureGroup {
    private String name;

    private List<FeatureDescription> list;

    public FeatureGroup(String name, List<FeatureDescription> list) {
        this.name = name;
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public List<FeatureDescription> getList() {
        return list;
    }
}
