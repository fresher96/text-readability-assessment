package tests;

import eu.crydee.syllablecounter.SyllableCounter;

public class SyllableCounterTest
{
	public static void main(String[] args)
	{
		SyllableCounter sc = new SyllableCounter();
		int myCount;
		
		myCount = sc.count("facility");
		myCount = sc.count("hello");
		myCount = sc.count("simplification");
		myCount = sc.count("blood");
		myCount = sc.count("extraction");
		
		System.out.println(myCount);
	}
}
