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
		
		printStream.print(prepare("file_name"));
		printStream.print(",");
		
		printStream.print(prepare("file_path"));
		printStream.print(",");
		
		for (String feature : featureList)
		{
			printStream.print(prepare(feature.toString()));
			printStream.print(",");
		}
		
		printStream.print(prepare("label"));
		printStream.print("\n");
	}
	
	@Override
	public void process(Document document, List<Object> features) {
		
		printStream.print(prepare(document.getName()));
		printStream.print(",");
		
		printStream.print(prepare(document.getPath()));
		printStream.print(",");
		
		for (Object value : features)
		{
			printStream.print(prepare(value.toString()));
			printStream.print(",");
		}
		
		printStream.print(prepare(document.getLabel()));
		printStream.print("\n");
	}
}
