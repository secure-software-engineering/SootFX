package core.rm;

import core.TestBase;
import core.fx.AssignStmtCountExtractor;
import core.fx.StmtCountExtractor;
import de.upb.swt.soot.core.model.SootClass;
import de.upb.swt.soot.core.model.SootMethod;
import org.junit.Test;

public class RichMethodTest extends TestBase {

    @Test
    public void testHelloWorld(){
        SootClass<?> sc = getSootClass("HelloWorld");
        SootMethod main = getSootMethod(sc, "main");
        RichMethod rm = new RichMethodBuilder(main).add(new StmtCountExtractor()).add(new AssignStmtCountExtractor()).build();
        System.out.println(rm);
    }
}
