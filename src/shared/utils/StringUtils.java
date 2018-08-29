package shared.utils;

public class StringUtils
{
	private StringUtils(){
	
	}
	
	public static boolean isNullOrEmpty(String str){
		return str == null || str.trim().equals("");
	}
	
	public static boolean isAWord(String str){
		for(int i=0; i<str.length(); i++)
		{
			if(Character.isAlphabetic(str.charAt(i)))
				return true;
		}
		return false;
	}
}
