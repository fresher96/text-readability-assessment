package fileio;

import com.sun.istack.internal.NotNull;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface DataFileReader
{
	void open() throws IOException;
	void close();
	boolean hasNext();
	String next();
}

/*interface ttt implements Iterable<>
{

}
*/
