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
	
	public final static int DEFAULT_WIDTH = 640;
	public final static int DEFAULT_HEIGHT = 480;
	
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
	
	public static String plot(Experiment experiment, String xAxis, String yAxis, String outFileName, CategoryDataset dataset) throws IOException {	
		CategoryAxis oxAxis = new CategoryAxis(xAxis);
		ValueAxis oyAxis = new NumberAxis(yAxis);
		CategoryItemRenderer renderer = new StatisticalBarRenderer();
		
		
		CategoryPlot plot = new CategoryPlot(dataset, oxAxis, oyAxis, renderer);
        plot.setOrientation(PlotOrientation.VERTICAL);

        JFreeChart chart = new JFreeChart(experiment.getExperimentDefinition().getExperimentName(), JFreeChart.DEFAULT_TITLE_FONT, plot, true);        
        
        String path = (new File(".")).getAbsolutePath() + outFileName + ".jpeg";
        File file = new File(path); 
        ChartUtilities.saveChartAsJPEG(file , chart , Plotter.DEFAULT_WIDTH , Plotter.DEFAULT_HEIGHT);
        return path;
	}

}
