package datasets;

import java.util.Iterator;

public interface TextCorpus extends Iterable<Document>
{
	public int size();
}
