package datasets.corpora;

import datasets.Document;

import java.util.Iterator;

public interface TextCorpus extends Iterable<Document>
{
	int size();
	
	default int chunk(){
		return -1;
	}
}
