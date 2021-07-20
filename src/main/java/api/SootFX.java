package api;


import core.fx.base.ClassFeatureExtractor;
import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import core.rm.ClassFeature;
import core.rm.MethodFeature;
import soot.G;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CHATransformer;
import soot.jimple.toolkits.callgraph.Edge;
import soot.options.Options;
import soot.util.Chain;

import java.util.*;

public class SootFX {

    private String mainClass;
    private List<String> classPaths;
    private Set<MethodFeature> methodFeatures;
    private Set<ClassFeature> classFeatures;
    private Set<SootClass> classes;
    private Set<SootMethod> methods;

    public SootFX addClassPath(String classPath) {
        if (this.classPaths == null) {
            this.classPaths = new ArrayList<>();
        }
        this.classPaths.add(classPath);
        return this;
    }

    public SootFX mainClass(String fullyQualifiedClassName) {
        this.mainClass = fullyQualifiedClassName;
        return this;
    }

    public Set<MethodFeature> extractMethodFeatures(Set<MethodFeatureExtractor> featureExtractors) {
        setupSoot(classPaths);
        //SootClass sc = Scene.v().forceResolve(mainClass, SootClass.BODIES);
        //Chain<SootClass> classes = Scene.v().getApplicationClasses();
        methodFeatures = new HashSet<>();
        methods = new HashSet<>();
        Iterator<Edge> cgIter = Scene.v().getCallGraph().iterator();
        while (cgIter.hasNext()){
            Edge edge = cgIter.next();
            methods.add(edge.src());
            methods.add(edge.tgt());
        }
        for(SootMethod method: methods){
            methodFeatures.add(extractMethodFeature(method, featureExtractors));
        }
        return methodFeatures;
    }

    public Set<ClassFeature> extractClassFeatures(Set<ClassFeatureExtractor> featureExtractors){
        setupSoot(classPaths);
        classFeatures = new HashSet<>();
        classes = new HashSet<>();
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

    private ClassFeature extractClassFeature(SootClass sootClass, Set<ClassFeatureExtractor> featureExtractors){
        ClassFeature rm = new ClassFeature(sootClass);
        for (ClassFeatureExtractor<?> featureExtractor : featureExtractors) {
            Feature feature = featureExtractor.extract(sootClass);
            rm.addFeature(feature);
        }
        return rm;
    }

    private MethodFeature extractMethodFeature(SootMethod sootMethod, Set<MethodFeatureExtractor> featureExtractors){
        MethodFeature rm = new MethodFeature(sootMethod);
        for (MethodFeatureExtractor<?> featureExtractor : featureExtractors) {
            Feature feature = featureExtractor.extract(sootMethod);
            rm.addFeature(feature);
        }
        return rm;
    }


    private void setupSoot(List<String> classPaths) {
        G.reset();
        Options.v().set_prepend_classpath(true);
        Options.v().set_include_all(true);
        Options.v().set_no_bodies_for_excluded(true);
        Options.v().set_allow_phantom_refs(true);
        Options.v().set_soot_classpath(System.getProperty("java.home") + "/lib/rt.jar");
        Options.v().set_src_prec(Options.src_prec_class);
        Options.v().set_process_dir(classPaths);
        Options.v().set_keep_line_number(true);
        // set spark options for construct call graphs
        Options.v().setPhaseOption("cg.spark", "on");
        Options.v().setPhaseOption("cg.spark", "string-constants:true");

        Options.v().set_whole_program(true);
        if(mainClass!=null){
            Options.v().set_main_class(mainClass);
        }
        Scene.v().loadNecessaryClasses();
        //SootMethod mainMethod = Scene.v().getMainMethod();
        //Scene.v().setEntryPoints(Collections.singletonList(mainMethod));

        HashMap opt = new HashMap();
        opt.put("enabled", "true");
        opt.put("vta","true");
        //opt.put("apponly","true");
        //SparkTransformer.v().transform("", opt);
        CHATransformer.v().transform();
    }

}
