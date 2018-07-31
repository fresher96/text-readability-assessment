package datasets;

import shared.MyUtils;
import shared.Pair;

import java.io.File;
import java.util.*;

public class LevelSeparatedTextCorpus extends TextCorpus
{
	private String path;
	private Object lock;
	private int classLimit;
	Random random;
	
	public Random getRandom() {
		return random;
	}
	
	public void setRandom(Random random) {
		this.random = random;
	}
	
	public int getClassLimit() {
		return classLimit;
	}
	
	public void setClassLimit(int classLimit) {
		this.classLimit = classLimit;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		if (this.path != null && !this.path.equals(path))
		{
			fileList = null;
		}
		
		this.path = path;
	}
	
	public LevelSeparatedTextCorpus() {
		this(null);
	}
	
	public LevelSeparatedTextCorpus(String path) {
		setPath(path);
		lock = new Object();
		classLimit = Integer.MAX_VALUE;
		random = new Random(0);
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
						Collections.shuffle(files, random);
						
						int index = 0;
						for (File file : files)
						{
							if(index >= classLimit) break;
							if (!file.isFile() || !file.getName().endsWith(".txt")) continue;
							
							index++;
							fileList.add(new Pair<>(file, dir.getName()));
						}
					}
				}
			}
		}
		
		return super.iterator();
	}
}
