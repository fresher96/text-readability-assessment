package datasets;

public class MakeLowerCaseCleaner implements TextCleaner
{
	@Override
	public void clean(Document document) {
		String text = document.getText();
		text = text.toLowerCase();
		document.setText(text);
	}
}
