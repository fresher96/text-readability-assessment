package shared;

import datasets.writers.DefaultCSVFeatureFile;

import java.io.*;
import java.util.Properties;

public class PropertiesManager
{
	private static PropertiesManager instance = null;
	private static Object lock = new Object();
	
	private Properties prop;
	private String path = "etc/config.properties";
	
	private PropertiesManager() throws IOException {
		prop = new Properties();
		
		InputStream input = new FileInputStream(path);
		
		prop.load(input);
	}
	
	public static PropertiesManager getInstance() throws IOException {
		if (instance == null)
		{
			synchronized (lock)
			{
				if (instance == null)
				{
					instance = new PropertiesManager();
				}
			}
		}
		
		return instance;
	}
	
	public String get(String key) {
		return prop.getProperty(key);
	}
	
	public void resetDefaults() throws IOException {
		OutputStream output = new FileOutputStream(path);
		
		prop.clear();
		prop.setProperty(DefaultCSVFeatureFile.class.getSimpleName(), "etc/features.csv");
		
		prop.store(output, null);
	}
}
