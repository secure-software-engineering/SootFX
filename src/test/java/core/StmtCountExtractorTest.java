package core;

import org.junit.Test;

public class StmtCountExtractorTest extends TestBase {


    @Test
    public void testHelloWorld(){
        extractorTest(new StmtCountExtractor(), "HelloWorld", "main", 4);
    }

    /**
     * id
     * return
     */
    @Test
    public void testEmptyMethod(){
        extractorTest(new StmtCountExtractor(),"EmptyMethod", "method", 2);
    }


}
