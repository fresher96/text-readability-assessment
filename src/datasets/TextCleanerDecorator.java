package datasets;

public class TextCleanerDecorator implements TextCleaner
{
	private TextCleaner textCleaner;
	
	public TextCleanerDecorator(TextCleaner textCleaner) {
		this.textCleaner = textCleaner;
	}
	
	@Override
	public void clean(Document document) {
		textCleaner.clean(document);
	}
}
