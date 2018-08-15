package shared.utils;

public class StringUtils
{
	private StringUtils(){
	
	}
	
	public static boolean isNullOrEmpty(String str){
		return str == null || str.trim().equals("");
	}
}
