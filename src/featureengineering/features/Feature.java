package featureengineering.features;

import nlp.NlpItem;

import java.lang.reflect.Field;

public interface Feature<T extends NlpItem>
{
	default String getName() {
		return this.getClass().getSimpleName();
	}
	
	default Object getValue() {
		try
		{
			Field field = this.getClass().getDeclaredField("value");
			return field.getInt(this);
		}
		catch (Exception ex)
		{
			throw new UnsupportedOperationException();
		}
	}
	
	default void reset() {
		try
		{
			Field field = this.getClass().getDeclaredField("value");
			field.setInt(this, 0);
		}
		catch (Exception ex)
		{
			throw new UnsupportedOperationException();
		}
	}
	
	void update(T arg);
}
