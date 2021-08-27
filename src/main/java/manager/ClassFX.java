package manager;

import api.FeatureDescription;
import core.fx.FxUtil;
import core.fx.base.ClassFEU;
import core.fx.base.Feature;
import core.rm.ClassFeatureSet;
import soot.Scene;
import soot.SootClass;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClassFX implements MultiInstanceFX<ClassFeatureSet, ClassFEU> {

    @Override
    public Set<ClassFeatureSet> getFeatures(Set<ClassFEU> featureExtractors) {
        Set<ClassFeatureSet> classFeatures = new HashSet<>();
        Set<SootClass> classes = new HashSet<>();
        Iterator<SootClass> classIter = Scene.v().getApplicationClasses().iterator();
        while(classIter.hasNext()){
            SootClass sc = classIter.next();
            if(FxUtil.isAppClass(sc)){
                classes.add(sc);
            }
        }
        for(SootClass sc: classes){
            classFeatures.add(extractClassFeature(sc, featureExtractors));
        }
        return classFeatures;
    }

    @Override
    public Set<ClassFeatureSet> getAllFeatures() {
        List<FeatureDescription> features = FxUtil.listAllClassFeatures();
        List<String> names = features.stream().map(f -> f.getName()).collect(Collectors.toList());
        return getFeatures(names);
    }

    @Override
    public Set<ClassFeatureSet> getAllFeaturesExclude(Set<String> exclusion) {
        List<FeatureDescription> features = FxUtil.listAllClassFeatures();
        List<String> names = features.stream().map(f -> f.getName()).collect(Collectors.toList());
        names.removeAll(exclusion);
        return getFeatures(names);
    }

    @Override
    public Set<ClassFeatureSet> getFeatures(List<String> featureExtractors) {
        Set<ClassFEU> fxSet = new HashSet<>();
        for(String str: featureExtractors){
            Class<?> cls = null;
            ClassFEU newInstance = null;
            try{
                cls = Class.forName("core.fx.classbased." + str);
                newInstance = (ClassFEU) cls.newInstance();
            }catch (InstantiationException e){
                //System.out.println("ignoring feature that takes an input value:" + str);
            } catch (Exception e){
                System.err.println("feature not found:" + str);
            }
            if(newInstance!=null){
                fxSet.add(newInstance);
            }
        }
        return getFeatures(fxSet);
    }

    private ClassFeatureSet extractClassFeature(SootClass sootClass, Set<ClassFEU> featureExtractors){
        ClassFeatureSet rm = new ClassFeatureSet(sootClass);
        for (ClassFEU<?> featureExtractor : featureExtractors) {
            Feature feature = featureExtractor.extract(sootClass);
            rm.addFeature(feature);
        }
        return rm;
    }
}
