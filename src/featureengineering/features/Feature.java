package featureengineering.features;

public interface Feature
{
	default String getName() {
		return this.getClass().getSimpleName();
	}
}
