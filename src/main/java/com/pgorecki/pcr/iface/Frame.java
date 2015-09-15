package com.pgorecki.pcr.iface;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.pgorecki.pcr.appliedBiosystems.Application;
import com.pgorecki.pcr.appliedBiosystems.Experiment;
import com.pgorecki.pcr.appliedBiosystems.Plotter;

public class Frame extends JFrame {
	
	private JPanel panel = new JPanel();
	private JTextArea configurationField;
	private JTextField xlsField;
	
	private static final int DEFAULT_WIDTH = 1300;
	private static final int DEFAULT_HEIGHT = 800;
	private static final long serialVersionUID = 1L;
	
	private static final String experimentDefinitionExample = "experiment: Experiment name\n" + 
"reference: RPL0\n" + 
"controll: 130404 RT 1,130404 RT 2,130404 RT 3\n" +
"3h: 130404 RT 4,130404 RT 5,130404 RT 6\n" +
"6h: 130404 RT 7,130404 RT 8,130404 RT 9";

	public Frame() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("PCR Analyzer");
//		setResizable(false);
		setLocationByPlatform(true);
		setVisible(true);
		
		Image icon = new ImageIcon("/home/piotr/PCRIco.png").getImage();
		setIconImage(icon);
		
		this.panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		constraints.ipadx = 20;
		JLabel xlsLabel = new JLabel("XLS file");		
		this.panel.add(xlsLabel, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.PAGE_START;
		xlsField = new JTextField("/home/piotr/workspace/20150904PBG_data.xls", 30);		
		this.panel.add(xlsField, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		JLabel configurationLabel = new JLabel("Configuration file");
		this.panel.add(configurationLabel, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		configurationField = new JTextArea(Frame.experimentDefinitionExample, 10, 30);
		this.panel.add(configurationField, constraints);
		
		constraints.gridx = 2;
		constraints.gridy = 2;
		constraints.gridwidth = 1;	
		constraints.anchor = GridBagConstraints.LINE_END;
		JButton proccessBtn = new JButton("Proccess");		
		ActionListener proccessAction = new ProccessAction(); 		
		proccessBtn.addActionListener(proccessAction);
		this.panel.add(proccessBtn, constraints);
		
		add(this.panel);
									
	}
	
	public void addPlot(String path, int gridx) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = gridx;
		constraints.gridy = 3;
		constraints.gridwidth = 4;
//		constraints.weightx = 0.5;
		constraints.insets = new Insets(20,0,0,0);
//		constraints.ipady = 300;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		this.panel.add(new PlotComponent(path), constraints);
		pack();
	}
	
	public Dimension getPreferredSize() { 
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT); 
	}
	
	
	private class ProccessAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			String experimentDefinition = configurationField.getText().trim();
			String xlsPath = xlsField.getText().trim();					
			
			if (!experimentDefinition.isEmpty() && !xlsPath.isEmpty()) {
				Experiment experiment = Application.Process(experimentDefinition, xlsPath);
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
	
}
