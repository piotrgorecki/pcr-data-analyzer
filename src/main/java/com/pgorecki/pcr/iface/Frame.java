package com.pgorecki.pcr.iface;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.pgorecki.pcr.appliedBiosystems.Application;
import com.pgorecki.pcr.appliedBiosystems.Experiment;
import com.pgorecki.pcr.appliedBiosystems.ExperimentDefinition;
import com.pgorecki.pcr.appliedBiosystems.Plotter;
import com.pgorecki.pcr.appliedBiosystems.Reporter;
import com.pgorecki.pcr.appliedBiosystems.XLSReader;

import utils.JarPath;

public class Frame extends JFrame {
	
	private final short IPAD_LABEL = 20;
	private final short TEXT_FIELD_SIZE = 50;	
	private static final int DEFAULT_WIDTH = 1500;
	private static final int DEFAULT_HEIGHT = 900;
	private static final long serialVersionUID = 1L;
	
	private int gridy = -1; 
	private JLabel xlsPathLabel1 = new JLabel();
	private JLabel xlsPathLabel2 = new JLabel();
	private JLabel xlsPathLabel3 = new JLabel();
	private JPanel panel = new JPanel();
	private JTextField experimentTitleField = new JTextField("Some experiment", TEXT_FIELD_SIZE);
	private JTextField refrenceField = new JTextField("RPL0", TEXT_FIELD_SIZE);
	private JTextField controlField = new JTextField("1,13,25", TEXT_FIELD_SIZE);
	private JTextField group1NameField = new JTextField("3h");
	private JTextField group2NameField = new JTextField("6h");
	private JTextField group3NameField = new JTextField("24h");
	private JTextField group4NameField = new JTextField();
	private JTextField group1TargetNameField = new JTextField("4,5,6");
	private JTextField group2TargetNameField = new JTextField("16,17,18");
	private JTextField group3TargetNameField = new JTextField("28,29,30");
	private JTextField group4TargetNameField = new JTextField();
	private JButton processBtn = new JButton("Process");	
	

	public Frame(String version) {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("qPCR Analyzer (v " + version + ")");
		setLocationByPlatform(true);
		setVisible(true);
		
		// Set icon
		URL path = getClass().getClassLoader().getResource("ico.png");
		Image icon = new ImageIcon(path).getImage();		
		setIconImage(icon);
		
		// Build JPanel with GridBagLayout
		this.panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints;
		
		// 1 row - Input file		
		addChoseFileRow(this.xlsPathLabel1);

		// 2 row - Input file
		addChoseFileRow(this.xlsPathLabel2);
		
		// 3 row - Input file
		addChoseFileRow(this.xlsPathLabel3);
					
		// 4 row - Experiment title
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = nextRow();
		constraints.gridwidth = 1;
		constraints.ipadx = IPAD_LABEL;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		constraints.fill = GridBagConstraints.VERTICAL;
		this.panel.add(new JLabel("Experiment title:"), constraints);
		
		constraints.gridx = 1;
		constraints.gridwidth = 3;
		this.panel.add(this.experimentTitleField, constraints);
				
		// 5 row - Reference
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = nextRow();
		constraints.gridwidth = 1;
		constraints.ipadx = IPAD_LABEL;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		constraints.fill = GridBagConstraints.VERTICAL;
		this.panel.add(new JLabel("Refrence:"), constraints);
		
		constraints.gridx = 1;
		constraints.gridwidth = 3;
		this.panel.add(this.refrenceField, constraints);
		
		// 6 row - Control
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = nextRow();
		constraints.gridwidth = 1;
		constraints.ipadx = 20;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		constraints.fill = GridBagConstraints.VERTICAL;
		this.panel.add(new JLabel("Control:"), constraints);
		
		constraints.gridx = 1;
		constraints.gridwidth = 3;
		this.panel.add(this.controlField, constraints);
		
		// 7 row - Group
		addGroupFields(this.group1NameField, this.group1TargetNameField);
		
		// 8 row - Group
		addGroupFields(this.group2NameField, this.group2TargetNameField);
		
		// 9 row - Group
		addGroupFields(this.group3NameField, this.group3TargetNameField);
		
		// 10 row - Group
		addGroupFields(this.group4NameField, this.group4TargetNameField);
							
		// 11 row - Process button
		constraints = new GridBagConstraints();
		constraints.gridx = 3;
		constraints.gridy = nextRow();
		constraints.gridwidth = 1;	
		constraints.anchor = GridBagConstraints.LINE_END;		
		this.processBtn.setEnabled(false);
		this.processBtn.addActionListener(new ProccessAction());
		this.panel.add(processBtn, constraints);
		
		// Add panel into the frame
		add(this.panel);
	}
	
	public void addPlot(String path, int gridx, int gridy) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = gridx;
		constraints.gridy = gridy;
		constraints.gridwidth = 4;
		constraints.insets = new Insets(20,0,0,0);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		this.panel.add(new PlotComponent(path), constraints);
		pack();
	}
	
	
	private void addGroupFields(JTextField nameField, JTextField targetField) {		
		int gridy = nextRow();
		
		//	  NameLabel
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = gridy;
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		this.panel.add(new JLabel("Group Name:"), constraints);

		//    NameField
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = gridy;
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.ipadx = 80;
		this.panel.add(nameField, constraints);

		//    TargetNameLabel
		constraints = new GridBagConstraints();
		constraints.gridx = 2;
		constraints.gridy = gridy;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(0, 10, 0, 10);
		this.panel.add(new JLabel("Targets:"), constraints);

		//    TargetNameField
		constraints = new GridBagConstraints();
		constraints.gridx = 3;
		constraints.gridy = gridy;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		this.panel.add(targetField, constraints);		
	}
	
	private void addChoseFileRow(JLabel label) {		
		int gridy = nextRow();
		// Label
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridy = gridy;
		constraints.gridx = 0;
		constraints.gridwidth = 1;
		constraints.ipadx = IPAD_LABEL;
		constraints.fill = GridBagConstraints.VERTICAL;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		JLabel xlsLabel = new JLabel("XLS file:");		
		this.panel.add(xlsLabel, constraints);

		// Label which is chosen file path
		constraints = new GridBagConstraints();
		constraints.gridy = gridy;
		constraints.gridx = 1;
		constraints.gridwidth = 3;		
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		label.setSize(TEXT_FIELD_SIZE, 20);
		this.panel.add(label, constraints);

		// Button
		JButton openFileBtn = new JButton("Choose a file");
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));			
		openFileBtn.addActionListener(new OpenFileAction(chooser, label));		
		constraints = new GridBagConstraints();
		constraints.gridy = gridy;
		constraints.gridx = 3;		
		constraints.gridwidth = 1;	
		constraints.anchor = GridBagConstraints.LINE_END;
		this.panel.add(openFileBtn, constraints);
	}
	
	private int nextRow() {
		this.gridy += 1;
		return this.gridy;
	}
	
	
	public Dimension getPreferredSize() { 
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT); 
	}
	
	
	private class ProccessAction implements ActionListener {		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (!xlsPathLabel1.getText().isEmpty() || !xlsPathLabel2.getText().isEmpty() || !xlsPathLabel3.getText().isEmpty()) {
				ExperimentDefinition experimentDefinition = new ExperimentDefinition(experimentTitleField.getText(), refrenceField.getText(), controlField.getText());
				
				addGroup(experimentDefinition, group1NameField, group1TargetNameField);
				addGroup(experimentDefinition, group2NameField, group2TargetNameField);
				addGroup(experimentDefinition, group3NameField, group3TargetNameField);
				addGroup(experimentDefinition, group4NameField, group4TargetNameField);
				
				Experiment experiment = new Experiment(experimentDefinition);
				
				if (!xlsPathLabel1.getText().isEmpty()) {
					XLSReader xls = new XLSReader(xlsPathLabel1.getText());
					xls.parseExperiment("Results", experiment);
				}
					
				
				if (!xlsPathLabel2.getText().isEmpty()) {
					XLSReader xls = new XLSReader(xlsPathLabel2.getText());
					xls.parseExperiment("Results", experiment);
				}
				
				if (!xlsPathLabel3.getText().isEmpty()) {
					XLSReader xls = new XLSReader(xlsPathLabel3.getText());
					xls.parseExperiment("Results", experiment);
					
				}
				
				Application.Process(experiment);

				String outDir = JarPath.getPath() + experiment.getName();								
				try {
					
					int gridy = nextRow();
					String meanΔΔcтPlotPath = Plotter.plot(experiment, "Group", "Mean ΔΔcт", "meanDDct", Plotter.getDatasetMeanΔΔcт(experiment), outDir);
					System.out.println(meanΔΔcтPlotPath);
					addPlot(meanΔΔcтPlotPath, 0, gridy);
					
					String rqPlotPath = Plotter.plot(experiment, "Group", "RQ", "rq", Plotter.getDatasetRQ(experiment), outDir);
					System.out.println(rqPlotPath);
					addPlot(rqPlotPath, 4, gridy);
					processBtn.setEnabled(false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				Reporter reporter = new Reporter(experiment);
				reporter.makeReport(outDir + File.separator + "report.xls");
				
			} else
				System.out.println("XLS file didn't chose");
		}
		
		private void addGroup(ExperimentDefinition experiment, JTextField groupName, JTextField targetName) {
			if (isFilled(groupName, targetName))
				experiment.addGroup(groupName.getText(), targetName.getText());
		}
		
		private Boolean isFilled(JTextField field1, JTextField field2) {
			return !field1.getText().isEmpty() && !field2.getText().isEmpty();
		}
	}
	
	private class OpenFileAction implements ActionListener {
		
		private JFileChooser chooser;
		private JLabel label;
		
		public OpenFileAction(JFileChooser chooser, JLabel label) {
			this.chooser = chooser;
			this.label = label;
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			this.chooser.setCurrentDirectory(new File("."));

			int result = this.chooser.showOpenDialog(panel);

			if (result == JFileChooser.APPROVE_OPTION) {
				label.setText(this.chooser.getSelectedFile().getPath());
				processBtn.setEnabled(true);
			}
		}
	}
	
}
