package core;

import org.junit.Test;

public class AssignStmtCountExtractorTest extends TestBase {

    @Test
    public void testHelloWorld(){
        extractorTest(new AssignStmtCountExtractor(), "HelloWorld", "main", 1);
    }

    /**
     * id
     * return
     */
    @Test
    public void testEmptyMethod(){
        extractorTest(new AssignStmtCountExtractor(),"EmptyMethod", "method", 0);
    }

}
