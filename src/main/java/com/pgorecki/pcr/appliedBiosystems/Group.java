package com.pgorecki.pcr.appliedBiosystems;

import java.util.ArrayList;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

public class Group {	
	
	private String name;
	private TargetName targetName;
	private Boolean isControll;
	private ArrayList<Counterpart> counterpartList = new ArrayList<Counterpart>();
	private ArrayList<String> sampleNameList = new ArrayList<String>();
	private Double meanΔΔcт;
	private Double semΔΔcт;
	private Double meanRq;
	private Double semRq;
	
	public Double getSemRq() {
		return semRq;
	}
	public void setSemRq(Double semRq) {
		this.semRq = semRq;
	}
	public Double getMeanRq() {
		return meanRq;
	}
	public void setMeanRq(Double meanRq) {
		this.meanRq = meanRq;
	}
	public Double getSemΔΔcт() {
		return semΔΔcт;
	}
	public void setSemΔΔcт(Double semΔΔcт) {
		this.semΔΔcт = semΔΔcт;
	}
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
	public Boolean isControll() {
		return this.isControll;
	}
	public Boolean isReference() {
		return this.targetName.getIsReference();
	}
	public Double getMeanΔΔcт() {
		return this.meanΔΔcт;
	}
	public void setMeanΔΔcт(Double meanΔΔcт) {
		this.meanΔΔcт = meanΔΔcт;
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
		return calculateAvg(sampleName);									
	}
	
	public Double calculateAvg(String sampleName) {
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
	

	
}