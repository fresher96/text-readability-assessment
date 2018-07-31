package datasets;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

public class CSVFileWriter extends FeatureFileWriter
{
	private PrintStream printStream;
	
	public CSVFileWriter() {
	
	}
	
	public CSVFileWriter(String path) {
		super(path);
	}
	
	@Override
	public void setPath(String path) {
		super.setPath(path);
		printStream = null;
	}
	
	public String prepare(String str) {
		
		String ret;
		ret = str.replaceAll("\\\\", "\\\\\\\\");
		ret = ret.replaceAll("\"", "\\\\\"");
		
		ret = "\"" + ret + "\"";
		return ret;
	}
	
	@Override
	public void writeHeaders(List<String> featureList) throws FileNotFoundException {
		printStream = new PrintStream(getPath());
		int index = -1;
		for (String feature : featureList)
		{
			index++;
			if (index != 0) printStream.print(",");
			printStream.print(prepare(feature));
		}
		printStream.print("\n");
	}
	
	@Override
	public void process(Document document, List<Object> features) {
		
		printStream.print(document.getName());
		printStream.print(",");
		
		printStream.print(document.getPath());
		printStream.print(",");
		
		for (Object value : features)
		{
			printStream.print(prepare(features.toString()));
			printStream.print(",");
		}
		
		printStream.print(document.getLabel());
		printStream.print("\n");
	}
}
