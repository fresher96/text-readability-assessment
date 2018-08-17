package featureengineering.featuresets;

import featureengineering.FeatureExtractor;
import featureengineering.features.CharCountFeature;
import featureengineering.features.Feature;
import nlp.NlpItem;
import nlp.NlpToken;
import shared.Debugger;
import shared.observer.Observable;
import shared.observer.Observer;

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
			
			if(value instanceof Feature)
			{
				ret.add(((Feature)value).getName());
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
			
			if(value instanceof Feature)
			{
				ret.add(((Feature)value).getValue());
			}
		}
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
			
			if(value instanceof Feature)
			{
				((Feature)value).update(arg);
			}
		}
	}
}

class SampleFeatureSet implements FeatureSet<NlpToken>
{
	private CharCountFeature chars = new CharCountFeature();
	featureengineering.features.WordCountFeature words = new featureengineering.features.WordCountFeature();
	private CharCountFeature chars2 = new CharCountFeature();
	protected String test = "hi";
	String testt;
	String ss = null;
}

class Test
{
	public static void main(String[] args){
		
		SampleFeatureSet sfs = new SampleFeatureSet();
		
		Debugger.debug(sfs.getFeatureList());
		Debugger.debug(sfs.getFeatures());
		
		sfs.update(new NlpToken()
		{
			@Override
			public String getRaw() {
				return "hi";
			}
		});
		
		sfs.update(new NlpToken()
		{
			@Override
			public String getRaw() {
				return "bye";
			}
		});
		
		Debugger.debug(sfs.getFeatures());
		
	}
}
