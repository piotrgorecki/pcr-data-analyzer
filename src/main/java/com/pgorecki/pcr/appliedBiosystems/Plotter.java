package com.pgorecki.pcr.appliedBiosystems;

import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StatisticalBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;

public class Plotter {
	
	public static DefaultStatisticalCategoryDataset getDatasetMeanΔΔcт(Experiment experiment) {
		DefaultStatisticalCategoryDataset dataset = new DefaultStatisticalCategoryDataset();
		for (Group group : experiment.getGroupList()) {
			if (group.isReference())
				continue;
			dataset.add(group.getMeanΔΔcт(), group.getSemΔΔcт(), group.getTargetName(), group.getName());
		}
		return dataset;
	}
	
	public static DefaultStatisticalCategoryDataset getDatasetRQ(Experiment experiment) {
		DefaultStatisticalCategoryDataset dataset = new DefaultStatisticalCategoryDataset();
		for (Group group : experiment.getGroupList()) {
			if (group.isReference())
				continue;
			dataset.add(group.getMeanRq(), group.getSemRq(), group.getTargetName(), group.getName());
		}
		return dataset;
	}
	
	public static void plot(Experiment experiment, String xAxis, String yAxis, String outFileName, CategoryDataset dataset) throws IOException {	
		CategoryAxis oxAxis = new CategoryAxis(xAxis);
		ValueAxis oyAxis = new NumberAxis(yAxis);
		CategoryItemRenderer renderer = new StatisticalBarRenderer();
		
		
		CategoryPlot plot = new CategoryPlot(dataset, oxAxis, oyAxis, renderer);
        plot.setOrientation(PlotOrientation.VERTICAL);

        JFreeChart chart = new JFreeChart(experiment.getExperimentDefinition().getExperimentName(), JFreeChart.DEFAULT_TITLE_FONT, plot, true);        
        
        int width = 640;
        int height = 480; 
        File pieChart = new File("/home/piotr/workspace/" + outFileName + ".jpeg"); 
        ChartUtilities.saveChartAsJPEG(pieChart , chart , width , height);
	}

}