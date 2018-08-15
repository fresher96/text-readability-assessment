package shared;

import java.util.List;

public class Debugger
{
	private Debugger() {
	
	}
	
	public static void debug(Object obj){
		System.out.println("\"" + obj.toString() + "\"");
	}
	
	public static <T> void debug(List<T> list){
		System.out.printf("[");
		int index = -1;
		for(T el : list){
			index++;
			if(index != 0) System.out.print(", ");
			System.out.print(el);
		}
		System.out.printf("]\n");
	}
}
