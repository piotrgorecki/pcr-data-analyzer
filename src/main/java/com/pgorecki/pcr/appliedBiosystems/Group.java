package com.pgorecki.pcr.appliedBiosystems;

import java.util.ArrayList;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.poi.ss.formula.eval.ConcatEval;

public class Group {	
	
	private String name;
	private Boolean isControll;
	private TargetName targetName;
	private ArrayList<Counterpart> counterpartList = new ArrayList<Counterpart>();
	private ArrayList<String> sampleNameList = new ArrayList<String>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTargetName() {
		return this.targetName.getName();
	}
	public ArrayList<String> getSampleNameList() {
		return this.sampleNameList;
	}
		
	public Group(String name, Boolean isControll, TargetName targetName) {
		super();
		this.name = name;
		this.targetName = targetName;
		this.isControll = isControll;
	}
	
	public void addSample(Well well, String sampleName) {
		
		Counterpart counterpart;
		
		if (isCounterpartForSampleName(sampleName))
			counterpart = getCounterpartForSampleName(sampleName);			
		else {
			counterpart = new Counterpart(sampleName);
			counterpartList.add(counterpart);
		}
		
		counterpart.add(well);	
		addSampleName(sampleName);
	}
	
	public Double getAvgFromCounterpart(String sampleName) {
						
		Counterpart counterpart = getCounterpartForSampleName(sampleName);		
		SummaryStatistics summaryStatistics = new SummaryStatistics();
		
		for (Well well : counterpart.getWellList())
			summaryStatistics.addValue(well.getCт());
		
		return summaryStatistics.getMean();				
	}
	
	
	private Boolean isCounterpartForSampleName(String sampleName) {
		for (Counterpart counterpart : this.counterpartList)
			if (counterpart.getSampleName().equals(sampleName))
				return true;
		return false;											
	}
	
	private Counterpart getCounterpartForSampleName(String sampleName) {
		for (Counterpart counterpart : this.counterpartList)
			if (counterpart.getSampleName().equals(sampleName))
				return counterpart;

		return null;
	}
	
	private void addSampleName(String sampleName) {
		if (!sampleNameList.contains(sampleName))
			this.sampleNameList.add(sampleName);
	}
	
	
	
	
    
    
//    private SummaryStatistics fillSummaryStatisticsBySamples(Sample.Field parameter) {
//    	SummaryStatistics summaryStatistics = new SummaryStatistics();
//    	
//    	for (Sample sample : sampleList)
//    		if (parameter == Sample.Field.CT_MEAN)
//    			summaryStatistics.addValue(sample.getCтMean());
//    		else if (parameter == Sample.Field.DCT_MEAN)
//    			summaryStatistics.addValue(sample.getΔcтMean());
//    		else if (parameter == Sample.Field.DCT_SE)
//    			summaryStatistics.addValue(sample.getΔcтSE());
//    		else if (parameter == Sample.Field.DDCT)
//    			summaryStatistics.addValue(sample.getΔΔcт());
//    		else if (parameter == Sample.Field.RQ)
//    			summaryStatistics.addValue(sample.getRq());
//    		else if (parameter == Sample.Field.RQ_MAX)
//    			summaryStatistics.addValue(sample.getRqMax());
//    		else if (parameter == Sample.Field.RQ_MIN)
//    			summaryStatistics.addValue(sample.getRqMin());    		
//    			
//		return summaryStatistics;
//    }

	
	
	
}
