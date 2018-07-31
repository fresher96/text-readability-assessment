package datasets;

import shared.PropertiesManager;

import java.io.IOException;

public class DefaultCSVFeatureFile extends CSVFileWriter
{
	public DefaultCSVFeatureFile() throws IOException {
		String path = PropertiesManager.getInstance().get(DefaultCSVFeatureFile.class.getSimpleName());
		setPath(path);
	}
}
