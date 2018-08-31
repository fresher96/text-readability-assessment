package featureengineering.cleaners;

import datasets.Document;

public interface TextCleaner
{
	void clean(Document document);
}
