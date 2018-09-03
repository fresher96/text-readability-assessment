package main;

import datasets.Document;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import featureengineering.cleaners.MakeLowerCaseCleaner;
import featureengineering.cleaners.TextCleaner;
import featureengineering.extractors.FeatureExtractor;
import featureengineering.extractors.PrototypeFeatureExtractorAdapter;
import shared.Debugger;
import shared.PropertiesManager;
import shared.utils.FileUtils;
import weka.classifiers.Classifier;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Application
{
	private JPanel pnlMain;
	private JButton btnLoad;
	private JButton btnClassify;
	private JComboBox comboClassifier;
	private JTextArea txtArea;
	private JLabel lblResult;
	
	private JFrame frame;
	private Cursor waitCursor = new Cursor(Cursor.WAIT_CURSOR);
	private Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
	
	private List<Classifier> classifierList;
	private FeatureExtractor featureExtractor;
	private Instances instances;
	
	public Application() {
		btnLoad.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				btnLoad_clicked(e);
			}
		});
		
		btnClassify.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				btnClassify_clicked(e);
			}
		});
	}
	
	private double[] castToDoubleArray(List<Object> list) {
		double[] ret = new double[list.size()];
		int index = -1;
		for (Object el : list)
		{
			index++;
			ret[index] = (double) list.get(index);
		}
		return ret;
	}
	
	private String classify(String text) {
		
		text = clean(text);
		double[] features = castToDoubleArray(featureExtractor.extract(text));
		
		Instance instance = new DenseInstance(1, features);
		instance.setDataset(instances);

//		for(double d : features) System.out.println(d);
		
		Classifier cls = classifierList.get(comboClassifier.getSelectedIndex());
		String ret;
		try
		{
			double result = cls.classifyInstance(instance);
			ret = instances.classAttribute().value((int) (result + 0.5));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			err(ex.getMessage());
			ret = "error, retry later";
		}
		
		return ret;
	}
	
	private String clean(String text) {
		Document doc = new Document(text);
		TextCleaner cleaner = new MakeLowerCaseCleaner();
		cleaner.clean(doc);
		return doc.getText();
	}
	
	private void btnClassify_clicked(ActionEvent e) {
		
		txtArea.setCursor(waitCursor);
		frame.setCursor(waitCursor);
		
		String text = txtArea.getText();
		lblResult.setText(classify(text));
		
		txtArea.setCursor(defaultCursor);
		frame.setCursor(defaultCursor);
	}
	
	private void btnLoad_clicked(ActionEvent e) {
		
		// if the path doesn't exist, then home directory will be used
		JFileChooser fileChooser = new JFileChooser("D:\\work space\\datasets\\OSE_cleaned\\5_texts_final\\1_Intermediate\\");
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));
		
		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue != JFileChooser.APPROVE_OPTION) return;
		File file = fileChooser.getSelectedFile();
		
		try
		{
			String text = FileUtils.readAllText(file);
			txtArea.setText(text);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			err(ex.getMessage(), "couldn't open the file");
		}
	}
	
	private void loadExtractor() {
		prototype.FeatureExtractor prototypeFeatures = new prototype.FeatureExtractor();
		
		prototype.TraditionalFeatureSet traditionalFeatureSet = new prototype.TraditionalFeatureSet();
		traditionalFeatureSet.addAllFeatures();
		prototypeFeatures.featureSetList.add(traditionalFeatureSet);
		
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		prototype.NlpFeatureSet nlpFeatureSet = new prototype.NlpFeatureSet(pipeline);
		prototypeFeatures.featureSetList.add(nlpFeatureSet);
		
		featureExtractor = new PrototypeFeatureExtractorAdapter(prototypeFeatures);
	}
	
	private void loadClassifiers() {
		classifierList = new ArrayList<>();
		instances = null;
		try
		{
			String modelsDirectory = PropertiesManager.getInstance().get("ModelsDirectory");
			List<File> modelList = FileUtils.getFiles(modelsDirectory);
			
			if (modelList.size() == 0) throw new Exception("no models found");
			
			for (File model : modelList)
			{
				String name = model.getName();
				
				Object[] o = SerializationHelper.readAll(modelsDirectory + name);
				Classifier cls = (Classifier) o[0];
				if (instances == null) instances = (Instances) o[1];
				
				classifierList.add(cls);
				
				if (!name.endsWith(".model")) throw new Exception("couldn't load model " + modelsDirectory + name);
				
				name = name.substring(0, name.length() - 6);
				comboClassifier.addItem(name);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			err(ex.getMessage(), "Error Loading Classifiers");
			System.exit(0);
		}
	}
	
	private void loadFrame() {
		frame = new JFrame("Text Readability Assessment");
		frame.setContentPane(pnlMain);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.pack();
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void err(String msg) {
		err(msg, "Unexpected Error");
	}
	
	private void err(String msg, String title) {
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public void run() {
		try
		{
			loadExtractor();
			loadClassifiers();
			loadFrame();
		}
		catch (Exception ex)
		{
			err(ex.getMessage());
		}
	}
	
	public static void main(String[] args) {
		Application app = new Application();
		app.run();
	}
}
