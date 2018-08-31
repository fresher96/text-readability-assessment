package featureengineering.cleaners;

import datasets.Document;

public class ExampleTextCleaner extends TextCleanerDecorator
{
	public ExampleTextCleaner(TextCleaner textCleaner){
		super(textCleaner);
	}
	
	public ExampleTextCleaner(){
	
	}
	
	@Override
	public void clean(Document document) {
		super.clean(document);
		
		document.setText(document.getText().substring(0, 10));
	}
}
