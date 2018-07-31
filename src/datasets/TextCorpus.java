package datasets;

import shared.MyUtils;
import shared.Pair;

import javax.xml.soap.Text;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class TextCorpus implements Iterable<LabeledDocument>
{
	private List<Pair<File, String>> fileList;
	
	public TextCorpus(){
		fileList = null;
	}
	
	@Override
	public Iterator<LabeledDocument> iterator() {
		return new DocumentIterator();
	}
	
	public int size(){
		return fileList.size();
	}
	
	private class DocumentIterator implements Iterator<LabeledDocument>
	{
		private int index;
		
		public DocumentIterator() {
			index = 0;
		}
		
		@Override
		public boolean hasNext() {
			return index < fileList.size();
		}
		
		@Override
		public LabeledDocument next() throws Exception {
			
			if (!this.hasNext())
			{
				throw new NoSuchElementException();
			}
			
			Pair<File, String> curFile = fileList.get(index);
			
			LabeledDocument ret = new LabeledDocument();
			ret.setLabel(curFile.getSecond());
			
			try
			{
				ret.setDocument(MyUtils.readAllText(curFile.getFirst()));
			}
			catch (IOException e)
			{
				e.printStackTrace();
				throw new NoSuchElementException();
			}
			
			return ret;
		}
	}
}


class LevelSeparatedTextCorpus extends TextCorpus
{

}
