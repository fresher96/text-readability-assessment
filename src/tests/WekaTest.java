package tests;

import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

import java.util.ArrayList;
import java.util.List;

public class WekaTest
{
	public static void main(String args[]) throws Exception {
//		readWriteTest();
		
		genMaskedFeatures();
	}
	
	private static void genMaskedFeatures() throws Exception {
		Instances data = ConverterUtils.DataSource.read("etc/joined_dataset.csv");
		
		int nBit = 7;
		for (int mask = 0; mask < (1 << nBit); mask++)
		{
			List<Integer> list = new ArrayList<>();
			for (int j = 0; j < nBit; j++)
			{
				if( (mask & (1<<j)) == 0) continue;
				list.add(j + 32);
			}
			
			int[] maskArr = new int[list.size()];
			String maskStr = "";
			for(int i=0; i<list.size(); i++)
			{
				maskArr[i] = list.get(i);
				maskStr += maskArr[i] + "_";
//				System.out.print(maskArr[i] + " ");
			}
			
//			System.out.println("\n");
//			if(true) continue;
			
			Remove remove = new Remove();
			remove.setAttributeIndicesArray(maskArr);
			remove.setInputFormat(data);
			Instances newData = Filter.useFilter(data, remove);
			
			ConverterUtils.DataSink.write(String.format("etc/Z_features__%s.csv", maskStr), newData);
		}
	}
	
	private static void readWriteTest() throws Exception {
		Instances data = ConverterUtils.DataSource.read("etc/joined_dataset.csv");
		
		//data.setClassIndex(data.numAttributes() - 1);

//		Instance x = data.firstInstance();
//		System.out.println(x);
//		System.out.println(data);
		
		
		Remove remove = new Remove();
		remove.setAttributeIndicesArray(new int[]{});
//		remove.setAttributeIndices("33-39");
		remove.setInputFormat(data);
		data = Filter.useFilter(data, remove);
		
		
		ConverterUtils.DataSink.write("etc/new_featsures.csv", data);


//		CSVLoader loader = new CSVLoader();
//		loader.setSource(new File("etc/features.csv"));
//		Instances data = loader.getDataSet();
	
	}
}
