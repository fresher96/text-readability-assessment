package shared.observer;

public interface Observer<T>
{
	void update(Observable<T> o, T arg);
}
