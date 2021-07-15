package core;

import core.fx.Feature;
import core.fx.MethodCountFeatureExtractor;
import de.upb.swt.soot.core.model.SootClass;
import de.upb.swt.soot.core.model.SootMethod;
import de.upb.swt.soot.java.bytecode.inputlocation.JavaClassPathAnalysisInputLocation;
import de.upb.swt.soot.java.core.JavaIdentifierFactory;
import de.upb.swt.soot.java.core.JavaProject;
import de.upb.swt.soot.java.core.JavaSootClass;
import de.upb.swt.soot.java.core.language.JavaLanguage;
import de.upb.swt.soot.java.core.types.JavaClassType;
import de.upb.swt.soot.java.core.views.JavaView;
import de.upb.swt.soot.java.sourcecode.inputlocation.JavaSourcePathAnalysisInputLocation;
import org.junit.Before;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class TestBase {

    protected JavaView view;

    @Before
    public void setupSoot() {
        String resourcesPath = "src/test/resources/";

        JavaProject javaProject =
                JavaProject.builder(new JavaLanguage(8))
                        .addInputLocation(
                                new JavaClassPathAnalysisInputLocation(
                                        System.getProperty("java.home") + "/lib/rt.jar"))
                        .addInputLocation(new JavaSourcePathAnalysisInputLocation(resourcesPath))
                        .build();

        view = javaProject.createOnDemandView();
    }

    protected SootClass<?> getSootClass(String targetTestClassName){
        JavaIdentifierFactory identifierFactory = JavaIdentifierFactory.getInstance();
        JavaClassType mainClassSignature = identifierFactory.getClassType(targetTestClassName);

        Optional<JavaSootClass> optClass = view.getClass(mainClassSignature);
        if(optClass.isPresent()){
            return optClass.get();
        }
        throw new RuntimeException("Class not found:" + targetTestClassName);
    }

    protected SootMethod getSootMethod(SootClass<?> sc, String methodName){
        Optional<? extends SootMethod> opt = sc.getMethods().stream().filter(e -> e.getName().equals(methodName)).findFirst();
        if(opt.isPresent()){
            return opt.get();
        }
        throw new RuntimeException("Method not found:" + methodName);
    }

    protected void extractorTest(MethodCountFeatureExtractor extractor, String className, String methodName, Integer count){
        SootClass<?> sc = getSootClass(className);
        SootMethod main = getSootMethod(sc, methodName);
        Feature<Integer> feature = extractor.extract(main);
        assertEquals(count, feature.getValue());
    }
}
