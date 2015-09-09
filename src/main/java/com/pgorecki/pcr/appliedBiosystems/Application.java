package com.pgorecki.pcr.appliedBiosystems;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

public class Application {
	
	private static Integer Δcтlength = 0;

	public static void main(String[] args) {

		ExperimentDefinition experimentDefinition = new ExperimentDefinition("/home/piotr/workspace/groups");
		
		XLSReader xls = new XLSReader("/home/piotr/workspace/20150904PBG_data.xls");
		Experiment experiment = xls.parseExperiment("Results", experimentDefinition);
				
		SummaryStatistics sem = new SummaryStatistics();
		
			
		Double avgΔcт = processAvgΔcт("3h", "MALAT1", experimentDefinition, experiment, false, sem);		
		Double controllAvgΔcт = processAvgΔcт("controll", "MALAT1", experimentDefinition, experiment, true, sem);
		
		Double ΔΔcт = avgΔcт - controllAvgΔcт;
		System.out.println("ΔΔcт: " + ΔΔcт);
		
		Double semΔcт = stderr(sem, Application.Δcтlength);
		System.out.println("semΔcт: " + semΔcт);
				

		
		
		
	}
	
	private static Double processAvgΔcт(String groupName, String targetName, ExperimentDefinition experimentDefinition, 
			Experiment experiment, Boolean isControll, SummaryStatistics sem) {		
		
		String[] sampleNameList;
		if (!isControll)
			sampleNameList = experimentDefinition.getSampleNameListForGroup(groupName);
		else
			sampleNameList = experimentDefinition.getSampleNameListForControll();
		
		System.out.println(groupName + ", " + targetName + ", is control? " + isControll + " :");
		
		SummaryStatistics summaryStatistics = new SummaryStatistics();		
		
		for (String sampleName : sampleNameList) {
			System.out.println(sampleName + ":");
			
			Group group = experiment.getGroup(groupName, targetName);
						
			String referenceGroup = experimentDefinition.getReference();
			Group groupReference = experiment.getGroup(groupName, referenceGroup);
			
			Double avg = group.getAvgFromCounterpart(sampleName);
			System.out.println("AVG counterpart: " + avg);
			
			Double avgReference = groupReference.getAvgFromCounterpart(sampleName);
			System.out.println("AVG reference counterpart: " + avgReference);
			
			Double Δcт = avg - avgReference;
			System.out.println("Δcт: " + Δcт);
			
			summaryStatistics.addValue(Δcт);
			sem.addValue(Δcт);
			Application.Δcтlength += 1;
		}
		Double avgΔcт = summaryStatistics.getMean();
		System.out.println("avgΔcт: " + avgΔcт);					
		
		return avgΔcт;
	}
	
	
	
	private static Double stderr(SummaryStatistics sem, Integer length) {
	    return sem.getStandardDeviation() / Math.sqrt(length);
	}

}
