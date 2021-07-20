package api;

import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import core.rm.MethodFeature;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class EntryClass {

    /*
    Set<MethodFeatureExtractor> featureExtractors;
    Set<MethodSignature> methodSignatures;
    JavaIdentifierFactory identifierFactory;
    JavaView view;
    Set<MethodFeature> methodFeatures = new HashSet<>();

    EntryClass(JavaView view, String className){
        this.view = view;
        ViewTypeHierarchy typeHierarchy = new ViewTypeHierarchy(view);
        RapidTypeAnalysisAlgorithm rta = new RapidTypeAnalysisAlgorithm(view, typeHierarchy);

        identifierFactory = JavaIdentifierFactory.getInstance();
        JavaClassType mainClassSignature = identifierFactory.getClassType(className);

        if(!view.getClass(mainClassSignature).isPresent()){
            throw new RuntimeException(String.format("Class {} does not exist", mainClassSignature.toString()));
        }

        MethodSignature mainMethodSignature =
                identifierFactory.getMethodSignature(
                        "main", mainClassSignature, "void", Collections.singletonList("java.lang.String[]"));

        CallGraph cg = rta.initialize(Collections.singletonList(mainMethodSignature));

        methodSignatures = cg.getMethodSignatures();
    }

    public Extract extract(Set<MethodFeatureExtractor> featureExtractors){
        this.featureExtractors = featureExtractors;
        for(MethodSignature sig: methodSignatures){
            Optional<? extends SootMethod> opt = view.getMethod(sig);
            if(opt.isPresent()){
                MethodFeature mf = extractMethodFeature(opt.get());
                methodFeatures.add(mf);
            }
        }
        return new Extract(methodFeatures);
    }

    private MethodFeature extractMethodFeature(SootMethod sootMethod){
        MethodFeature rm = new MethodFeature(sootMethod);
        for (MethodFeatureExtractor<?> featureExtractor : featureExtractors) {
            Feature feature = featureExtractor.extract(sootMethod);
            rm.addFeature(feature);
        }
        return rm;
    }
*/
}
