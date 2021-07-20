package sourcecode.pkg;

public class A{

	public void method(){
		internalMethod("str", 1);
	}

	private void internalMethod(String s, int i){
		int a = i+1;
	}

}