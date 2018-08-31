package featureengineering.cleaners;

import datasets.Document;

public abstract class TextCleanerDecorator implements TextCleaner
{
	private TextCleaner textCleaner;
	
	public TextCleanerDecorator(TextCleaner textCleaner) {
		this.textCleaner = textCleaner;
	}
	
	public TextCleanerDecorator(){
		this(null);
	}
	
	@Override
	public void clean(Document document) {
		if(textCleaner != null)
			textCleaner.clean(document);
	}
}
