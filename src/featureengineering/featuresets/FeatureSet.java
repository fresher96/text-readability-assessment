package featureengineering.featuresets;

import featureengineering.features.Feature;
import nlp.NlpItem;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public interface FeatureSet<T extends NlpItem>
{
	default List<String> getFeatureList() {
		List<String> ret = new ArrayList<>();
		for (Field field : this.getClass().getDeclaredFields())
		{
			field.setAccessible(true);
			Object value = null;
			
			try
			{
				value = field.get(this);
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			
			if (value instanceof Feature)
			{
				ret.add(((Feature) value).getName());
			}
		}
		return ret;
	}
	
	default List<Object> getFeatures() {
		List<Object> ret = new ArrayList<>();
		for (Field field : this.getClass().getDeclaredFields())
		{
			field.setAccessible(true);
			Object value = null;
			
			try
			{
				value = field.get(this);
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			
			if (value instanceof Feature)
			{
				ret.add(((Feature) value).getValue());
			}
		}
		
		reset();
		return ret;
	}
	
	default void update(T arg) {
		for (Field field : this.getClass().getDeclaredFields())
		{
			field.setAccessible(true);
			Object value = null;
			
			try
			{
				value = field.get(this);
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			
			if (value instanceof Feature)
			{
				((Feature) value).update(arg);
			}
		}
	}
	
	default void reset(){
		for (Field field : this.getClass().getDeclaredFields())
		{
			field.setAccessible(true);
			Object value = null;
			
			try
			{
				value = field.get(this);
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			
			if (value instanceof Feature)
			{
				((Feature) value).reset();
			}
		}
	}
	
}
