package test;

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
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import shared.Debugger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Test
{
	
	private static void writeConfigFile() throws IOException {
		Properties prop = new Properties();
		OutputStream output = new FileOutputStream("config.properties");
		
		prop.setProperty("datab=sase", "=tra\\=shs");
		prop.setProperty("dbus=er", "mkyong");
		prop.setProperty("dbpassword", "password");
		
		prop.store(output, null);
	}
	
	private static void readConfigFile() throws IOException {
		Properties prop = new Properties();
		InputStream input = new FileInputStream("config.properties");
		
		prop.load(input);
		
		System.out.println(prop.getProperty("datab=sase"));
		System.out.println(prop.getProperty("dbus=er"));
		System.out.println(prop.getProperty("dbpassword"));
	}
	
	
	private static void nlpTest() {
		String text = "Jim bought 300 shares of Acme Corp. in 2006.";
		
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
	
	public static void main(String[] args) throws IOException {
		nlpTest();
	}
	
	
}
