package tests;

import shared.Debugger;
import shared.PropertiesManager;

import java.io.*;
import java.util.Properties;

public class ConfigsTest
{
	private static void writeConfigFile() throws IOException {
		Properties prop = new Properties();
		OutputStream output = new FileOutputStream("etc/config.properties");
		
		prop.setProperty("datab=sase", "=tra\\=shs");
		prop.setProperty("dbus=er", "mkyong");
		prop.setProperty("dbpassword", "password");
		
		prop.store(output, null);
	}
	
	private static void readConfigFile() throws IOException {
		Properties prop = new Properties();
		InputStream input = new FileInputStream("etc/config.properties");
		
		prop.load(input);
		
		System.out.println(prop.getProperty("datab=sase"));
		System.out.println(prop.getProperty("dbus=er"));
		System.out.println(prop.getProperty("dbpassword"));
	}
	
	public static void main(String[] args) throws IOException {
//		writeConfigFile();
//		readConfigFile();
		
		PropertiesManager prop = PropertiesManager.getInstance();
		System.out.println(prop.get("hi"));
		Debugger.debug(prop.get("heyHowAreYou"));
		Debugger.debug(prop.get("soo"));
		
//		prop.set("hi", "you");
//		prop.set("soo", "too");
		
		System.out.println(prop.get("hi"));
		
		System.gc();
	}
}
