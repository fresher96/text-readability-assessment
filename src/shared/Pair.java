package shared;

public class Pair<T1, T2>
{
	private T1 first;
	private T2 second;
	
	public Pair() {
		
	}
	
	public Pair(T1 first, T2 second) {
		setFirst(first);
		setSecond(second);
	}
	
	public T1 getFirst() {
		return first;
	}
	
	public void setFirst(T1 first) {
		this.first = first;
	}
	
	public T2 getSecond() {
		return second;
	}
	
	public void setSecond(T2 second) {
		this.second = second;
	}
	
	@Override
	public String toString() {
		String ret = String.format("(%s, %s)", first.toString(), second.toString());
		return ret;
	}
}
