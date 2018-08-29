package main;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
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
	private Cursor defaulCursor = new Cursor(Cursor.DEFAULT_CURSOR);
	
	private List<Classifier> classifierList;
	private FeatureExtractor featureExtractor;
	private Instances instances;
	
	public Application() {
		
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
		
		
		// loading classifiers
		classifierList = new ArrayList<>();
		try
		{
			String modelsDirectory = PropertiesManager.getInstance().get("ModelsDirectory");
			List<File> modelList = FileUtils.getFiles(modelsDirectory);
			
			if(modelList.size() == 0) throw new Exception("no models found");
			
			for (File model : modelList)
			{
				String name = model.getName();
				
				Object[] o = SerializationHelper.readAll(modelsDirectory + name);
				Classifier cls = (Classifier) o[0];
				instances = (Instances) o[1];
				System.out.println(instances);
				
				classifierList.add(cls);
				
				if (!name.endsWith(".model")) throw new Exception("couldn't load model " + modelsDirectory + name);
				
				name = name.substring(0, name.length() - 6);
				
				comboClassifier.addItem(name);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error Loading Classifiers", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
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
	
	public <T, E extends T> List<E> cast(List<T> list) {
		List<E> ret = new ArrayList<>();
		for (T el : list)
		{
			ret.add((E) el);
		}
		return ret;
	}
	
	public double[] castToDoubleArray(List<Object> list) {
		double[] ret = new double[list.size()];
		int index = -1;
		for (Object el : list)
		{
			index++;
			ret[index] = (double)list.get(index);
		}
		return ret;
	}
	
	
	private void btnClassify_clicked(ActionEvent e) {
		
		txtArea.setCursor(waitCursor);
		frame.setCursor(waitCursor);
		
		String text = txtArea.getText();
		double[] features = castToDoubleArray(featureExtractor.extract(text));
		
		Instance instance = new DenseInstance(1, features);
		instance.setDataset(instances);
		
		for(double d : features) System.out.println(d);
		
		Classifier cls = classifierList.get(comboClassifier.getSelectedIndex());
		double result = 0;
		try
		{
			result = cls.classifyInstance(instance);
//			String resClass = instance.
			lblResult.setText(instances.classAttribute().value((int)(result + 0.5)));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			lblResult.setText("Result Label");
		}
		
		
		txtArea.setCursor(defaulCursor);
		frame.setCursor(defaulCursor);
	}
	
	private void btnLoad_clicked(ActionEvent e) {
		
		JFileChooser fileChooser = new JFileChooser("D:\\work space\\datasets\\OSE_cleaned\\5_texts_final\\1_Intermediate\\");
//		JFileChooser fileChooser = new JFileChooser();
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
			JOptionPane.showMessageDialog(null, ex.getMessage(), "couldn't open the file", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	public static void main(String[] args) {
		
		Application application = new Application();
		
		application.frame = new JFrame("Text Readability Assessment");
		application.frame.setContentPane(application.pnlMain);
		application.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		application.frame.pack();
		application.frame.setSize(500, 500);
		application.frame.setLocationRelativeTo(null);
		application.frame.setVisible(true);
		
//		frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
//		application.pnlMain.setCursor(new Cursor(Cursor.WAIT_CURSOR));
//		application.txtArea.setCursor(new Cursor(Cursor.WAIT_CURSOR));
	}
}
