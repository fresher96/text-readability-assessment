package main;

import shared.utils.FileUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Application
{
	private JPanel pnlMain;
	private JButton btnLoad;
	private JButton btnClassify;
	private JComboBox comboClassifier;
	private JTextArea txtArea;
	private JLabel lblResult;
	
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
	
	private void btnClassify_clicked(ActionEvent e) {
	
	}
	
	private void btnLoad_clicked(ActionEvent e) {
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Files (*.txt)","txt"));
		
		int returnValue = fileChooser.showOpenDialog(null);
		if(returnValue != JFileChooser.APPROVE_OPTION) return;
		File file = fileChooser.getSelectedFile();
		
		try
		{
			String text = FileUtils.readAllText(file);
			txtArea.setText(text);
		}
		catch (IOException ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "couldn't open file", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void main(String[] args)
	{
		Application application = new Application();
		
		JFrame frame = new JFrame("Text Readability Assessment");
		frame.setContentPane(application.pnlMain);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.pack();
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
