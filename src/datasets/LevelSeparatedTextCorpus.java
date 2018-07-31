package datasets;

import shared.MyUtils;
import shared.Pair;

import java.io.File;
import java.util.*;

public class LevelSeparatedTextCorpus extends TextCorpus
{
	private String path;
	private Object lock;
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		if (!path.equals(this.path))
		{
			fileList = null;
		}
		
		this.path = path;
	}
	
	public LevelSeparatedTextCorpus() {
		setPath(null);
		lock = new Object();
	}
	
	public LevelSeparatedTextCorpus(String path) {
		this();
		setPath(path);
	}
	
	@Override
	public Iterator<Document> iterator() {
		if (fileList == null)
		{
			synchronized (lock)
			{
				if (fileList == null)
				{
					List<File> dirList = MyUtils.getFiles(path);
					fileList = new ArrayList<>();
					for (File dir : dirList)
					{
						if (!dir.isDirectory()) continue;
						
						List<File> files = MyUtils.getFiles(dir.getPath());
						for (File file : files)
						{
							if (!file.isFile() || !file.getName().endsWith(".txt")) continue;
							
							fileList.add(new Pair<>(file, dir.getName()));
						}
					}
				}
			}
		}
		
		return super.iterator();
	}
}
