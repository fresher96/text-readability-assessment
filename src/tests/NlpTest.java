package tests;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import edu.stanford.nlp.trees.tregex.TregexPatternCompiler;
import edu.stanford.nlp.util.CoreMap;
import shared.Pair;

import java.io.*;
import java.util.*;

public class NlpTest
{
	private static void nlpTest() {
		String text;
		
//		text = "Jim bought 300 shares of Acme Corp. in 2006.";
		text = "We use it when a Jørgen in our dorm IS acting LIkE a SpOiled child.";
		
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, depparse, ner, parse, dcoref");
		props.setProperty("pos.model", "edu/stanford/nlp/models/pos-tagger/english-bidirectional/english-bidirectional-distsim.tagger");
		props.setProperty("ner.model", "edu/stanford/nlp/models/ner/english.all.3class.distsim.crf.ser.gz,edu/stanford/nlp/models/ner/english.conll.4class.distsim.crf.ser.gz,edu/stanford/nlp/models/ner/english.muc.7class.distsim.crf.ser.gz");
		
		Annotation document = new Annotation(text);
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		pipeline.annotate(document);
		
		
		// these are all the sentences in this document
		// a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		
		for (CoreMap sentence : sentences)
		{
			// traversing the words in the current sentence
			// a CoreLabel is a CoreMap with additional token-specific methods
			for (CoreLabel token : sentence.get(TokensAnnotation.class))
			{
				// this is the text of the token
				String word = token.get(TextAnnotation.class);
				
				// this is the POS tag of the token
				String pos = token.get(PartOfSpeechAnnotation.class);
				
				// lemma
				String lemma = token.get(LemmaAnnotation.class);
				
				// this is the NER label of the token
				String ne = token.get(NamedEntityTagAnnotation.class);
				
				
				System.out.println("word: " + word + " | pos: " + pos + " | lemma: " + lemma + " | ne:" + ne);
			}
			
			// this is the parse tree of the current sentence
			Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
			System.out.println("parse tree:\n" + tree);
			
			// this is the Stanford dependency graph of the current sentence
			SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
			System.out.println("dependency graph:\n" + dependencies);
		}
		
		
		// This is the co-reference link graph
		// Each chain stores a set of mentions that link to each other,
		// along with a method for getting the most representative mention
		// Both sentence and token offsets start at 1!
		Map<Integer, CorefChain> graph = document.get(CorefCoreAnnotations.CorefChainAnnotation.class);
	}
	
	private static void tregexTest() {
		String text;
		text = "hi, how are you?";
		text = "We it use whn we is fine, you";
		text = "hi my name is Sue, how you do?\r\n" +
				"Increased cloud cover in periods of normally clear weather is closing Lukla Airport, the gateway to the Everest region, more often. A new road for 4x4s is being built to Lukla to guarantee the flow of tourists and their money, but Byers is worried that the rapid spread of the road network in Nepal is being done too cheaply, with disastrous consequences in terms of soil erosion and landslides.\r\n" +
				"Everest is the icon everyone knows, he says. Its the perfect laboratory for figuring out how";
		text = "\"Everest is the mountain everyone talks about\", he says.";
		//text = "We use it when a girl in our dorm is acting like a spoiled child.";
		
		
		
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse");
		
		Annotation document = new Annotation(text);
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		pipeline.annotate(document);
		
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		CoreMap sentence = sentences.get(0);
		Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
		
		//tree.printLocalTree();
		tree.pennPrint();
		//System.out.println(tree);
		System.out.printf("score: %f | size: %d | depth: %d | leaves#: %d \n", tree.score(), tree.size(), tree.depth(), tree.getLeaves().size());
		
		String pattern = "S|SINV|SQ < (VP <# MD|VBD|VBP|VBZ)";
		TregexPatternCompiler tpc = new TregexPatternCompiler();
		TregexPattern p0 = tpc.compile(pattern);
		TregexMatcher m0 = p0.matcher(tree);
		
		int count = 0;
		while(m0.find())
		{
			m0.getMatch().pennPrint();
			count++;
		}
		
		// expected 2
		System.out.println("count = " + count);
	}
	
	
	public static void main(String[] args) throws IOException {
//		nlpTest();
		//tregexTest();
	}
	
	
}
