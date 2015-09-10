package com.pgorecki.pcr.appliedBiosystems;

import java.util.ArrayList;

public class Application {
	
	public static void main(String[] args) {

		ExperimentDefinition experimentDefinition = new ExperimentDefinition("/home/piotr/workspace/groups");
		
		XLSReader xls = new XLSReader("/home/piotr/workspace/20150904PBG_data.xls");
		Experiment experiment = xls.parseExperiment("Results", experimentDefinition);
				
		
		for (Group group : experiment.getGroupList()) {
			if (group.isReference())
				continue;						
			
			String targetName = group.getTargetName();
			String groupName = group.getName();
			
			System.out.println("\n----------- " + groupName + ", " + targetName + " ----------- :");
					
			ArrayList<Double> ΔcтListControll = processΔcт("controll", targetName, experiment);
			Double avgΔcтControll = mean(ΔcтListControll);
			int listSize = ΔcтListControll.size();
			
			ArrayList<Double> ΔcтList;
			
			if (group.isControll()) {
				ArrayList<Double> clone = new ArrayList<>(listSize);
				for (Double i : ΔcтListControll) clone.add(i);
				ΔcтList = clone;
			}
			else
				ΔcтList = processΔcт(groupName, targetName, experiment);
			
			
			ArrayList<Double> ΔΔcтList = new ArrayList<>(listSize);
			ArrayList<Double> rqList = new ArrayList<>(listSize);
			
			for (Double Δcт : ΔcтList) {
				Double ΔΔcт = Δcт - avgΔcтControll;
				ΔΔcтList.add(ΔΔcт);
				System.out.print("ΔΔcт: " + ΔΔcт + ", ");
				Double rqValue = rq(ΔΔcт);
				System.out.println("rqValue: " + rqValue);
				rqList.add(rqValue);
			}
			
			
			Double avgΔΔcт = mean(ΔΔcтList);
			System.out.println("avgΔΔcт: " + avgΔΔcт);
			
			Double semΔΔcт = stderr(ΔΔcтList);
			System.out.println("semΔΔcт: " + semΔΔcт);
			
			Double avgRq = mean(rqList);
			System.out.println("avgRq: " + avgRq);
			
			Double semRq = stderr(rqList);
			System.out.println("semRq: " + semRq);
		
		}
				
	}
	
	private static ArrayList<Double> processΔcт(String groupName, String targetName, Experiment experiment) {		
		ExperimentDefinition experimentDefinition = experiment.getExperimentDefinition();
		Boolean isControll = groupName.equals(ExperimentDefinition.CONTROL_GROUP_NAME);
		
		String[] sampleNameList;
		if (!isControll)
			sampleNameList = experimentDefinition.getSampleNameListForGroup(groupName);
		else
			sampleNameList = experimentDefinition.getSampleNameListForControll();						
			
		ArrayList<Double> ΔcтList = new ArrayList<>(sampleNameList.length);
		
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
			ΔcтList.add(Δcт);
			System.out.println("Δcт: " + Δcт);			
		}
		
		ΔcтList.trimToSize();
		return ΔcтList;
	}
	
	
	public static double mean(ArrayList<Double> list) {
	    double tot = 0.0;
	    for (Double i : list)
	      tot += i;
	    return tot / list.size();
	}
	public static double sdev(ArrayList<Double> list) {
	    return Math.sqrt(variance(list));
	}
	private static Double stderr(ArrayList<Double> list) {
		return sdev(list) / Math.sqrt(list.size());
	}
	public static double variance(ArrayList<Double> list) {
	    double mu = mean(list);
	    double sumsq = 0.0;
	    for (Double i : list)
	      sumsq += sqr(mu - i);
	    return sumsq / (list.size());
	}
	public static double sqr(double x) {
	    return x * x;
	}
	public static Double rq(Double exponent) {
		return Math.pow(2, -exponent);
	}
	

}
