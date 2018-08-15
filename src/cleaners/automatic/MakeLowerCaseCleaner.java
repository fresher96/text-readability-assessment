package cleaners.automatic;

import datasets.Document;

public class MakeLowerCaseCleaner extends TextCleanerDecorator
{
	public MakeLowerCaseCleaner(TextCleaner textCleaner){
		super(textCleaner);
	}
	
	public MakeLowerCaseCleaner(){
	
	}
	
	@Override
	public void clean(Document document) {
		super.clean(document);
		
		document.setText(document.getText().toLowerCase());
	}
}
