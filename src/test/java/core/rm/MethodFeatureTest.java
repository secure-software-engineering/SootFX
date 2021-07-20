package core.rm;

import core.TestBase;
import core.fx.methodbased.AssignStmtCount;
import core.fx.methodbased.StmtCount;
import org.junit.Test;
import soot.SootMethod;

public class MethodFeatureTest extends TestBase {

   /* @Test
    public void testHelloWorld(){
        SootClass<?> sc = getSootClass("HelloWorld");
        SootMethod main = getSootMethod(sc, "main");
        MethodFeature rm = new MethodFeatureBuilder(main).add(new StmtCount()).add(new AssignStmtCount()).build();
        System.out.println(rm);
    }

    */
}
