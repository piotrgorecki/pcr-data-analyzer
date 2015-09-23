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
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.pgorecki.pcr.appliedBiosystems.Application;
import com.pgorecki.pcr.appliedBiosystems.Experiment;
import com.pgorecki.pcr.appliedBiosystems.Plotter;

public class Frame extends JFrame {
	
	private JLabel xlsPathLabel;
	private JPanel panel = new JPanel();
	private JTextField experimentTitleField;
	private JTextField refrenceField;
	private JTextField controllField;
	private JTextArea groupsArea;
	private JTextField group1NameField = new JTextField("3h");
	private JTextField group2NameField = new JTextField("6h");
	private JTextField group3NameField = new JTextField();
	private JTextField group4NameField = new JTextField();
	private JTextField group1TargetNameField = new JTextField("130404 RT 4,130404 RT 5,130404 RT 6");
	private JTextField group2TargetNameField = new JTextField("130404 RT 7,130404 RT 8,130404 RT 9");
	private JTextField group3TargetNameField = new JTextField();
	private JTextField group4TargetNameField = new JTextField();
	
	private final short TEXT_FIELD_SIZE = 50;	
	private static final int DEFAULT_WIDTH = 1300;
	private static final int DEFAULT_HEIGHT = 800;
	private static final long serialVersionUID = 1L;
	

	public Frame() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("PCR Analyzer (v 0.2)");
		setLocationByPlatform(true);
		setVisible(true);
		
		URL path = getClass().getClassLoader().getResource("ico.png");
		Image icon = new ImageIcon(path).getImage();		
		setIconImage(icon);
		
		this.panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints;
		
		// 1 row
		constraints = new GridBagConstraints();
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.gridwidth = 1;
		constraints.ipadx = 20;
		constraints.fill = GridBagConstraints.VERTICAL;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		JLabel xlsLabel = new JLabel("XLS file:");		
		this.panel.add(xlsLabel, constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridy = 0;
		constraints.gridx = 1;
		constraints.gridwidth = 3;		
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		this.xlsPathLabel = new JLabel();
		this.xlsPathLabel.setSize(TEXT_FIELD_SIZE, 20);
		this.panel.add(this.xlsPathLabel, constraints);
		

		JButton openFileBtn = new JButton("Choose a file");
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));			
		openFileBtn.addActionListener(new OpenFileAction(chooser));		
		constraints = new GridBagConstraints();
		constraints.gridy = 0;
		constraints.gridx = 3;		
		constraints.gridwidth = 1;	
		constraints.anchor = GridBagConstraints.LINE_END;
		this.panel.add(openFileBtn, constraints);

		
		// Experiment title
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.ipadx = 20;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		constraints.fill = GridBagConstraints.VERTICAL;
		this.panel.add(new JLabel("Experiment title:"), constraints);
		
		constraints.gridx = 1;
		constraints.gridwidth = 3;
		this.experimentTitleField = new JTextField("Some experiment", TEXT_FIELD_SIZE);
		this.panel.add(this.experimentTitleField, constraints);
				
		// Reference
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.ipadx = 20;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		constraints.fill = GridBagConstraints.VERTICAL;
		this.panel.add(new JLabel("Refrence:"), constraints);
		
		constraints.gridx = 1;
		constraints.gridwidth = 3;
		this.refrenceField = new JTextField("RPL0", TEXT_FIELD_SIZE);
		this.panel.add(this.refrenceField, constraints);
		
		// Control
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.ipadx = 20;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		constraints.fill = GridBagConstraints.VERTICAL;
		this.panel.add(new JLabel("Control:"), constraints);
		
		constraints.gridx = 1;
		constraints.gridwidth = 3;
		this.controllField = new JTextField("130404 RT 1,130404 RT 2,130404 RT 3", TEXT_FIELD_SIZE);
		this.panel.add(this.controllField, constraints);
		
		
		addGroupFields(4, this.group1NameField, this.group1TargetNameField);
		addGroupFields(5, this.group2NameField, this.group2TargetNameField);
		addGroupFields(6, this.group3NameField, this.group3TargetNameField);
		addGroupFields(7, this.group4NameField, this.group4TargetNameField);
		
					
		// PROCESS
		constraints = new GridBagConstraints();
		constraints.gridx = 3;
		constraints.gridy = 78;
		constraints.gridwidth = 1;	
		constraints.anchor = GridBagConstraints.LINE_END;
		JButton proccessBtn = new JButton("Proccess");		
		proccessBtn.addActionListener(new ProccessAction());
		this.panel.add(proccessBtn, constraints);
		
		add(this.panel);
	}
	
	public void addPlot(String path, int gridx) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = gridx;
		constraints.gridy = 3;
		constraints.gridwidth = 4;
		constraints.insets = new Insets(20,0,0,0);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		this.panel.add(new PlotComponent(path), constraints);
		pack();
	}
	
	
	private void addGroupFields(int rowNo, JTextField nameField, JTextField targetField) {
		//	  NameLabel
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = rowNo;
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		this.panel.add(new JLabel("Group Name:"), constraints);

		//    NameField
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = rowNo;
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.ipadx = 80;
		this.panel.add(nameField, constraints);

		//    TargetNameLabel
		constraints = new GridBagConstraints();
		constraints.gridx = 2;
		constraints.gridy = rowNo;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(0, 10, 0, 10);
		this.panel.add(new JLabel("Targets:"), constraints);

		//    TargetNameField
		constraints = new GridBagConstraints();
		constraints.gridx = 3;
		constraints.gridy = rowNo;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		this.panel.add(targetField, constraints);
	}
	
	
	public Dimension getPreferredSize() { 
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT); 
	}
	
	
	private class ProccessAction implements ActionListener {		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			String experimentDefinition = groupsArea.getText().trim();
			
			if (!experimentDefinition.isEmpty() && !xlsPathLabel.getText().isEmpty()) {
				Experiment experiment = Application.Process(experimentDefinition, xlsPathLabel.getText());
				try {
					String meanΔΔcтPlotPath = Plotter.plot(experiment, "Group", "Mean ΔΔcт", "meanDDct", Plotter.getDatasetMeanΔΔcт(experiment));
					System.out.println(meanΔΔcтPlotPath);
					addPlot(meanΔΔcтPlotPath, 0);
					
					String rqPlotPath = Plotter.plot(experiment, "Group", "RQ", "rq", Plotter.getDatasetRQ(experiment));
					System.out.println(rqPlotPath);
					addPlot(rqPlotPath, 4);
				} catch (IOException e1) {
					e1.printStackTrace();
				}						
			} else
				System.out.println("Ohhh");
		}		
	}
	
	private class OpenFileAction implements ActionListener {
		
		private JFileChooser chooser;
		
		public OpenFileAction(JFileChooser chooser) {
			this.chooser = chooser;
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			this.chooser.setCurrentDirectory(new File("."));

			int result = this.chooser.showOpenDialog(panel);

			if (result == JFileChooser.APPROVE_OPTION)
				xlsPathLabel.setText(this.chooser.getSelectedFile().getPath());

		}
	}
	
}
