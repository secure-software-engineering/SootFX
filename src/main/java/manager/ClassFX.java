package manager;

import core.fx.base.ClassFeatureExtractor;
import core.fx.base.Feature;
import core.rm.ClassFeatureSet;
import soot.Scene;
import soot.SootClass;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ClassFX implements MultiInstanceFX<ClassFeatureSet, ClassFeatureExtractor> {

    @Override
    public Set<ClassFeatureSet> getFeatures(Set<ClassFeatureExtractor> featureExtractors) {
        Set<ClassFeatureSet> classFeatures = new HashSet<>();
        Set<SootClass> classes = new HashSet<>();
        Iterator<SootClass> classIter = Scene.v().getApplicationClasses().iterator();
        while(classIter.hasNext()){
            SootClass sc = classIter.next();
            classes.add(sc);
        }
        for(SootClass sc: classes){
            classFeatures.add(extractClassFeature(sc, featureExtractors));
        }
        return classFeatures;
    }

    private ClassFeatureSet extractClassFeature(SootClass sootClass, Set<ClassFeatureExtractor> featureExtractors){
        ClassFeatureSet rm = new ClassFeatureSet(sootClass);
        for (ClassFeatureExtractor<?> featureExtractor : featureExtractors) {
            Feature feature = featureExtractor.extract(sootClass);
            rm.addFeature(feature);
        }
        return rm;
    }
}
