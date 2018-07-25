package shared;

public class Debugger
{
	private Debugger() {
	
	}
	
	public static void debug(Object obj){
		System.out.println("\"" + obj.toString() + "\"");
	}
}
