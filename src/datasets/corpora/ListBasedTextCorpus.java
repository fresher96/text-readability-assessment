package datasets.corpora;

import datasets.Document;
import shared.utils.FileUtils;
import shared.Pair;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class ListBasedTextCorpus implements TextCorpus
{
	protected List<Pair<File, String>> fileList;
	
	public ListBasedTextCorpus() {
		fileList = null;
	}
	
	@Override
	public Iterator<Document> iterator() {
		return new DocumentIterator();
	}
	
	public int size() {
		if (fileList == null) iterator();
		return fileList.size();
	}
	
	private class DocumentIterator implements Iterator<Document>
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
		public Document next() throws NoSuchElementException {
			
			if (!this.hasNext())
			{
				throw new NoSuchElementException();
			}
			
			Pair<File, String> curPair = fileList.get(index);
			index++;
			
			File curFile = curPair.getFirst();
			
			
			Document ret = new Document();
			
			ret.setLabel(curPair.getSecond());
			ret.setName(curFile.getName());
			ret.setPath(curFile.getPath());
			
			try
			{
				ret.setText(FileUtils.readAllText(curFile));
			}
			catch (IOException e)
			{
				e.printStackTrace();
				throw new NoSuchElementException("document: \"" + ret.getPath() + "\"");
			}
			
			return ret;
		}
	}
}


