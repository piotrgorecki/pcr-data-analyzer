package com.pgorecki.pcr.iface;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import com.pgorecki.pcr.appliedBiosystems.Plotter;

public class PlotComponent extends JComponent {
		
	private static final int DEFAULT_WIDTH = Plotter.DEFAULT_WIDTH;
	private static final int DEFAULT_HEIGHT = Plotter.DEFAULT_HEIGHT;
	private static final long serialVersionUID = 1L;
	private Image plot;
	
	public PlotComponent(String plotPath) {
		 this.plot = new ImageIcon(plotPath).getImage();
	}
	
	public void paintComponent(Graphics g) {
		if (this.plot == null) return;
		
		g.drawImage(this.plot, 0, 0, null);				
	}
	
	public Dimension getPreferredSize() { 
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT); 
	}

}
