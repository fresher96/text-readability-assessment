package prototype;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import edu.stanford.nlp.trees.tregex.TregexPatternCompiler;
import edu.stanford.nlp.util.CoreMap;
import shared.Debugger;

import java.util.*;

public class NlpFeatureSet extends FeatureSet
{
	private StanfordCoreNLP nlpParser;
	
	public NlpFeatureSet(StanfordCoreNLP nlpParser) {
		this.nlpParser = nlpParser;
	}
	
	@Override
	public List<String> addedFeatures() { // rename it to getSelectedFeatures
		List<String> ret = new ArrayList<>();
		for (NlpFeatureSet.Features feature : selectedFeatures)
		{
			ret.add(feature.name());
		}
		return ret;
	}
	
	EnumSet<Features> selectedFeatures = EnumSet.allOf(Features.class);
	
	public enum Features
	{
		AvgTreeDepth,
		AvgTreeScore,
		
		MLC,
		MLS,
		MLT,
		
		C_S,
		
		C_T,
		CT_T,
		DC_C,
		DC_T,
		
		CP_C,
		CP_T,
		T_S,
		
		CN_C,
		CN_T,
		VP_T,
		
		/* others */
		
		SentenceCount,
		AvgSentenceLengthInWords,
		AvgSentenceLengthInChars,
		
		NounVariation,
		VerbVariation,
		AdjectiveVariation,
		AdverbVariation,
		
		VerbVariation1,
		SquaredVerbVariation1,
		CorrectedVerbVariation1,
		
		LexicalDensity,
	}
	
	@Override
	public List<Feature> extract(String document) {
		
		double nWord = 0;
		double nChar = 0;
		
		double depth = 0;
		double score = 0;
		
		double nSentence = 0;
		double nClause = 0;
		double nTunit = 0;
		
		double nComplexTunit = 0;
		double nDependentClause = 0;
		
		double nCoordinatePhrase = 0;
		
		double nComplexNominal = 0;
		double nVerbPhrase = 0;
		
		/* others */
		
		double nNoun = 0;
		double nVerb = 0;
		double nAdjective = 0;
		double nAdverb = 0;
		
		
		/* computation */
		
		TregexPatternCompiler tpc = new TregexPatternCompiler();
		Set<String> uniqueStemmedVerbs = new HashSet<>();
		
		Annotation doc = new Annotation(document);
		nlpParser.annotate(doc);
		
		List<CoreMap> sentenceList = doc.get(CoreAnnotations.SentencesAnnotation.class);
		for (CoreMap sentence : sentenceList)
		{
			Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
			
			//nWord += tree.getLeaves().size();
			
			depth += tree.depth();
			score += tree.score();
			
			nClause += count("S|SINV|SQ < (VP <# MD|VBD|VBP|VBZ)", tree, tpc) + count("FRAG > ROOT !<< VP", tree, tpc);
			nSentence++;
			nTunit += count("S|SBARQ|SINV|SQ > ROOT | [$-- S|SBARQ|SINV|SQ !>> SBAR|VP]", tree, tpc) + count("FRAG > ROOT", tree, tpc);
			
			/* empty */
			
			nDependentClause += count("SBAR < (S|SINV|SQ < (VP <# MD|VBD|VBP|VBZ))", tree, tpc);
			nComplexTunit += count("S|SBARQ|SINV|SQ [> ROOT | [$-- S|SBARQ|SINV|SQ !>> SBAR|VP]] << (SBAR < (S|SQ|SINV < (VP <# MD|VBP|VBZ|VBD)))", tree, tpc);
			
			nCoordinatePhrase += count("ADJP|ADVP|NP|VP < CC", tree, tpc);
			
			nComplexNominal += count("NP !> NP [<< JJ|POS|PP|S|VBG |<< (NP $++ NP !$+ CC)]", tree, tpc);
			nComplexNominal += count("SBAR [$+ VP | > VP] & [<# WHNP |<# (IN < That|that|For|for) |<, S]", tree, tpc);
			nComplexNominal += count("S < (VP <# VBG|TO) $+ VP", tree, tpc);
			nVerbPhrase += count("ADJP|ADVP|NP|VP < CC", tree, tpc);
			
			/* others */
			
			List<CoreLabel> tokenList = sentence.get(CoreAnnotations.TokensAnnotation.class);
			for (CoreLabel token : tokenList)
			{
				String rawToken = token.get(CoreAnnotations.TextAnnotation.class);
				String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
				
				nWord++;
				nChar += rawToken.length();
				
				if (pos.equals("NN") || pos.equals("NNS") || pos.equals("NNP") ||
						pos.equals("NNPS"))
				{
					nNoun++;
				}
				else if (pos.equals("VB") || pos.equals("VBD") || pos.equals("VBG") ||
						pos.equals("VBN") || pos.equals("VBP") || pos.equals("VBZ"))
				{
					nVerb++;
					String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);
					uniqueStemmedVerbs.add(lemma);
				}
				else if (pos.equals("JJ") || pos.equals("JJR") || pos.equals("JJS"))
				{
					nAdjective++;
				}
				else if (pos.equals("RB") || pos.equals("RBR") || pos.equals("RBS"))
				{
					nAdverb++;
				}
			}
		}
		
		double nLexicalToken = nNoun + nVerb + nAdjective + nAdverb;
		double nUniqueStemmedVerb = uniqueStemmedVerbs.size();
		
		/* returning results */
		
		List<Feature> ret = new ArrayList<>();
		
		ret.add(new Feature(-1, "", div(depth, nSentence)));
		ret.add(new Feature(-1, "", div(score, nSentence)));
		
		ret.add(new Feature(-1, "", div(nWord, nClause)));
		ret.add(new Feature(-1, "", div(nWord, nSentence)));
		ret.add(new Feature(-1, "", div(nWord, nTunit)));
		
		ret.add(new Feature(-1, "", div(nClause, nSentence)));
		
		ret.add(new Feature(-1, "", div(nClause, nTunit)));
		ret.add(new Feature(-1, "", div(nComplexTunit, nTunit)));
		ret.add(new Feature(-1, "", div(nDependentClause, nClause)));
		ret.add(new Feature(-1, "", div(nDependentClause, nTunit)));
		
		ret.add(new Feature(-1, "", div(nCoordinatePhrase, nClause)));
		ret.add(new Feature(-1, "", div(nCoordinatePhrase, nTunit)));
		ret.add(new Feature(-1, "", div(nTunit, nSentence)));
		
		ret.add(new Feature(-1, "", div(nComplexNominal, nClause)));
		ret.add(new Feature(-1, "", div(nComplexNominal, nTunit)));
		ret.add(new Feature(-1, "", div(nVerbPhrase, nTunit)));
		
		/* others */
		
		ret.add(new Feature(-1, "", nSentence));
		ret.add(new Feature(-1, "", div(nWord, nSentence)));
		ret.add(new Feature(-1, "", div(nChar, nSentence)));
		
		ret.add(new Feature(-1, "", div(nNoun, nLexicalToken)));
		ret.add(new Feature(-1, "", div(nVerb, nLexicalToken)));
		ret.add(new Feature(-1, "", div(nAdjective, nLexicalToken)));
		ret.add(new Feature(-1, "", div(nAdverb, nLexicalToken)));
		
		ret.add(new Feature(-1, "", div(nUniqueStemmedVerb, nVerb)));
		ret.add(new Feature(-1, "", div(nUniqueStemmedVerb * nUniqueStemmedVerb, nVerb)));
		ret.add(new Feature(-1, "", div(nUniqueStemmedVerb, Math.sqrt(2 * nVerb))));
		
		ret.add(new Feature(-1, "", div(nLexicalToken, nWord)));
		
		
		
		return ret;
	}
	
	private double div(double nWord, double nClause) {
		if (Math.abs(nClause) < 1e-6) return 0;
		return nWord / nClause;
	}
	
	private int count(String tregex, Tree tree, TregexPatternCompiler tpc) {
		
		TregexPattern tp = tpc.compile(tregex);
		TregexMatcher tm = tp.matcher(tree);
		
		int ret = 0;
		while (tm.find())
		{
			//tm.getMatch().pennPrint();
			ret++;
		}
		
		return ret;
	}
	
}
