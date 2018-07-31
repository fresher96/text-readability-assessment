package datasets;

import shared.Pair;

public class LabeledDocument
{
	Pair<String, String> labeledDocument;
	
	public LabeledDocument() {
		labeledDocument = new Pair<>();
	}
	
	public LabeledDocument(String document, String label) {
		this();
		setDocument(document);
		setLabel(label);
	}
	
	public String getDocument() {
		return labeledDocument.getFirst();
	}
	
	public void setDocument(String document) {
		labeledDocument.setFirst(document);
	}
	
	public String getLabel() {
		return labeledDocument.getSecond();
	}
	
	public void setLabel(String label) {
		labeledDocument.setSecond(label);
	}
}
