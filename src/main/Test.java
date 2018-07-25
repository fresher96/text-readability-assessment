package main;

import shared.Debugger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Test
{
	public static void main(String[] args) throws IOException {
	
	}
	
	private static void writeConfigFile() throws IOException {
		Properties prop = new Properties();
		OutputStream output = new FileOutputStream("config.properties");
		
		prop.setProperty("datab=sase", "=tra\\=shs");
		prop.setProperty("dbus=er", "mkyong");
		prop.setProperty("dbpassword", "password");
		
		prop.store(output, null);
	}
	
	private static void readConfigFile() throws IOException {
		Properties prop = new Properties();
		InputStream input = new FileInputStream("config.properties");
		
		prop.load(input);
		
		System.out.println(prop.getProperty("datab=sase"));
		System.out.println(prop.getProperty("dbus=er"));
		System.out.println(prop.getProperty("dbpassword"));
	}
}
