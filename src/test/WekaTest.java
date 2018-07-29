package test;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils;

import java.io.File;

public class WekaTest
{
	public static void main(String args[]) throws Exception {
		readWriteTest();
	}
	
	private static void readWriteTest() throws Exception {
		Instances data = ConverterUtils.DataSource.read("etc/features.csv");
		
		//data.setClassIndex(data.numAttributes() - 1);

//		Instance x = data.firstInstance();
//		System.out.println(x);
//		System.out.println(data);
		
		
		
		
		
		
		
		
		ConverterUtils.DataSink.write("etc/new_features.csv", data);


//		CSVLoader loader = new CSVLoader();
//		loader.setSource(new File("etc/features.csv"));
//		Instances data = loader.getDataSet();
	
	}
}
