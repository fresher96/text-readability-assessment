package featureengineering.features;

import nlp.NlpItem;

public interface Feature<T extends NlpItem>
{
	default String getName() {
		return this.getClass().getSimpleName();
	}
	
	default Object getValue(){
		throw new UnsupportedOperationException();
	}
	
	void update(T arg);
}
