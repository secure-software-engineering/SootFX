package core;

import de.upb.swt.soot.core.model.SootClass;
import de.upb.swt.soot.core.model.SootMethod;
import de.upb.swt.soot.core.signatures.MethodSignature;
import de.upb.swt.soot.java.bytecode.inputlocation.JavaClassPathAnalysisInputLocation;
import de.upb.swt.soot.java.core.JavaIdentifierFactory;
import de.upb.swt.soot.java.core.JavaProject;
import de.upb.swt.soot.java.core.language.JavaLanguage;
import de.upb.swt.soot.java.core.types.JavaClassType;
import de.upb.swt.soot.java.core.views.JavaView;
import de.upb.swt.soot.java.sourcecode.inputlocation.JavaSourcePathAnalysisInputLocation;
import org.junit.Before;

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

        return view.getClass(mainClassSignature).get();
    }

    protected SootMethod getSootMethod(SootClass<?> sc, String methodName){
        return sc.getMethods().stream().filter(e -> e.getName().equals(methodName)).findFirst().get();
    }

    protected void extractorTest(MethodCountFeatureExtractor extractor, String className, String methodName, Integer count){
        SootClass<?> sc = getSootClass(className);
        SootMethod main = getSootMethod(sc, methodName);
        Feature<Integer> feature = extractor.extract(main);
        assertEquals(count, feature.getValue());
    }
}
