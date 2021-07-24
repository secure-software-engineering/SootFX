package sourcecode.pkg;

public class A{

	public static void main(String[] args){
		A a = new A();
		a.method();
	}

	public void method(){
		internalMethod("str", 1);
	}

	private void internalMethod(String s, int i){
		int a = i+1;
	}

}