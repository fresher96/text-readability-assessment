package shared;

public class Timer
{
	public static final int NANOPERSECOND = 1000 * 1000 * 1000;
	
	private long startTime = 0;
	private long endTime = 0;
	
	public void start() {
		startTime = System.nanoTime();
	}
	
	public void stop() {
		endTime = System.nanoTime();
	}
	
	public int elapsedTime() {
		return (int) ((endTime - startTime) / NANOPERSECOND);
	}
	
	@Override
	public String toString() {
		int elapsedTime = elapsedTime();
		
		int hours = elapsedTime / 3600;
		elapsedTime %= 3600;
		
		int minutes = elapsedTime / 60;
		elapsedTime %= 60;
		
		int seconds = elapsedTime;
		
		String ret = String.format("%02d:%02d:%02d", hours, minutes, seconds);
		return ret;
	}
}
