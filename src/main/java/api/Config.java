package api;

import java.util.List;

public class Config {
    private List<String> methodFeatureInclusion;
    private List<String> methodFeatureExclusion;

    private List<String> classFeatureInclusion;
    private List<String> classFeatureExclusion;

    private List<String> wholeProgFeatureInclusion;
    private List<String> wholeProgFeatureExclusion;

    private List<String> manifestFeatureInclusion;
    private List<String> manifestFeatureExclusion;

    private List<FeatureResource> featureResources;


    public List<String> getMethodFeatureInclusion() {
        return methodFeatureInclusion;
    }

    public void setMethodFeatureInclusion(List<String> methodFeatureInclusion) {
        this.methodFeatureInclusion = methodFeatureInclusion;
    }

    public List<String> getMethodFeatureExclusion() {
        return methodFeatureExclusion;
    }

    public void setMethodFeatureExclusion(List<String> methodFeatureExclusion) {
        this.methodFeatureExclusion = methodFeatureExclusion;
    }

    public List<String> getClassFeatureInclusion() {
        return classFeatureInclusion;
    }

    public void setClassFeatureInclusion(List<String> classFeatureInclusion) {
        this.classFeatureInclusion = classFeatureInclusion;
    }

    public List<String> getClassFeatureExclusion() {
        return classFeatureExclusion;
    }

    public void setClassFeatureExclusion(List<String> classFeatureExclusion) {
        this.classFeatureExclusion = classFeatureExclusion;
    }

    public List<String> getWholeProgFeatureInclusion() {
        return wholeProgFeatureInclusion;
    }

    public void setWholeProgFeatureInclusion(List<String> wholeProgFeatureInclusion) {
        this.wholeProgFeatureInclusion = wholeProgFeatureInclusion;
    }

    public List<String> getWholeProgFeatureExclusion() {
        return wholeProgFeatureExclusion;
    }

    public void setWholeProgFeatureExclusion(List<String> wholeProgFeatureExclusion) {
        this.wholeProgFeatureExclusion = wholeProgFeatureExclusion;
    }

    public List<String> getManifestFeatureInclusion() {
        return manifestFeatureInclusion;
    }

    public void setManifestFeatureInclusion(List<String> manifestFeatureInclusion) {
        this.manifestFeatureInclusion = manifestFeatureInclusion;
    }

    public List<String> getManifestFeatureExclusion() {
        return manifestFeatureExclusion;
    }

    public void setManifestFeatureExclusion(List<String> manifestFeatureExclusion) {
        this.manifestFeatureExclusion = manifestFeatureExclusion;
    }

    public List<FeatureResource> getFeatureResources() {
        return featureResources;
    }

    public void setFeatureResources(List<FeatureResource> featureResources) {
        this.featureResources = featureResources;
    }
}
