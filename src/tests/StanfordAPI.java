package tests;

import java.util.List;
import java.util.Properties;


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
import edu.stanford.nlp.util.CoreMap;


public class StanfordAPI
{
	public static StanfordAPI instance;
	private StanfordCoreNLP pipeline;
	
	public StanfordAPI() {
		Properties props = new Properties();
		
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, depparse, ner");
		props.setProperty("pos.model", "edu/stanford/nlp/models/pos-tagger/english-bidirectional/english-bidirectional-distsim.tagger");
		props.setProperty("ner.model", "edu/stanford/nlp/models/ner/english.all.3class.distsim.crf.ser.gz,edu/stanford/nlp/models/ner/english.conll.4class.distsim.crf.ser.gz,edu/stanford/nlp/models/ner/english.muc.7class.distsim.crf.ser.gz");
		//props.setProperty("depparse.extradependencies", "SUBJ_ONLY");

		pipeline = new StanfordCoreNLP(props);
	}
	
	
	public void test(String text) {
		Annotation document = new Annotation(text);
		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		int i = -1;
		for (CoreMap sentence : sentences)
		{
			i++;
			
		}
	}
	
	public void run(String text) {
		Annotation document = new Annotation(text);
		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		int i = -1;
		for (CoreMap sentence : sentences)
		{
			i++;
			if (sentence.toString().startsWith("#"))
				continue;
			for (int t = 0; t < sentence.get(TokensAnnotation.class).size(); t++)
			{
				CoreLabel token = sentence.get(TokensAnnotation.class).get(t);
				String raw_word = token.get(TextAnnotation.class);
				String pos_tag = token.get(PartOfSpeechAnnotation.class);
				String lemma = token.get(LemmaAnnotation.class);
				String ner = token.get(NamedEntityTagAnnotation.class);
				System.out.println(raw_word + "," + pos_tag + "," + lemma + " , " + ner);
			}
			SemanticGraph dependency_graph = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
			dependency_graph.prettyPrint();
			for (SemanticGraphEdge edge : dependency_graph.edgeIterable())
			{
				GrammaticalRelation rel = edge.getRelation();
				//if (rel.getShortName().equals("nsubj"))
				{
					String dep = edge.getDependent().lemma();
					String gov = edge.getGovernor().lemma();
					System.out.println(dep + " --- " + gov);
				}
			}
			
		}
	}
	
	public static void main(String[] args) {
		StanfordAPI.instance = new StanfordAPI();
		//StanfordAPI.instance.tests("Jim bought 300 shares of Acme Corp. in 2006.");
		StanfordAPI.instance.run("Jim bought 300 shares of Acme Corp. in 2006.");
	}
}
