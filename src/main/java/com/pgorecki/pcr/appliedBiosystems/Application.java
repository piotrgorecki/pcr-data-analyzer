package com.pgorecki.pcr.appliedBiosystems;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.pgorecki.pcr.iface.Frame;


public class Application {

	final static String VERSION = "1.0";

	public static void main(String[] args) throws IOException {

		EventQueue.invokeLater(new Runnable() {			
			public void run() {
				new Frame(VERSION);
			}
		});

	}

	public static void verify(Experiment experiment) throws Exception {
		ExperimentDefinition experimentDefinition = experiment.getExperimentDefinition();

		// Are expected control groups in xls input file?
		ArrayList<String> expConList = experiment.getControlList();
		ArrayList<String> expDefConList = new ArrayList<String>();
		for (String controlName: experimentDefinition.getControlGroupList())
			expDefConList.add(controlName);

		boolean res = expDefConList.removeAll(expConList);
		if (!res || !expDefConList.isEmpty())
			throw new Exception("In XLS input file there is no control group of: \"" + expDefConList + "\".");


		// Are expected references in xls input file?
		if (!experiment.hasReference())
			throw new Exception("In XLS input file there is no reference called: \"" + experimentDefinition.getReference() + "\".");


		// Are targets per group in xls input file (as Sample Name)?
		ArrayList<Group> experimentGroupList = experiment.getGroupList(); // excluding the control group

		// For each group in definition
		for (HashMap<String, ArrayList<String>> groupHashMap : experimentDefinition.getGroupList()) {
			String groupNameDef = groupHashMap.keySet().iterator().next();
			ArrayList<String> targetNameDefArr = new ArrayList<String>();
			for (String s : groupHashMap.get(groupNameDef))
				targetNameDefArr.add(s);
			
			ArrayList<String> sampleNameExperimentList = new ArrayList<>();

			for (Group group : experimentGroupList)
				if (group.getName().equals(groupNameDef)) {		
					sampleNameExperimentList = group.getSampleNameList();

					targetNameDefArr.removeAll(sampleNameExperimentList);

					if (! targetNameDefArr.isEmpty())
						throw new Exception("In XLS input file there is no sample: \"" 
								+ targetNameDefArr + "\" which is part of \""
								+ group.getName() + "\" group.");					
					break;
				}			
		}
	}

	public static Experiment process(Experiment experiment) {		
		for (Group group : experiment.getGroupList()) {
			if (group.isReference())
				continue;						

			String targetName = group.getTargetName();
			String groupName = group.getName();

			System.out.println("\n----------- " + groupName + ", " + targetName + " ----------- :");

			ArrayList<Double> ΔcтListControl = processΔcт("control", targetName, experiment);
			double avgΔcтControl = mean(ΔcтListControl);
			group.setAvgΔcтControl(avgΔcтControl);
			int listSize = ΔcтListControl.size();

			ArrayList<Double> ΔcтList;

			if (group.isControl()) {
				ArrayList<Double> clone = new ArrayList<>(listSize);
				for (Double i : ΔcтListControl) clone.add(i);
				ΔcтList = clone;
			}
			else
				ΔcтList = processΔcт(groupName, targetName, experiment);


			ArrayList<Double> ΔΔcтList = new ArrayList<>(listSize);
			ArrayList<Double> rqList = new ArrayList<>(listSize);

			for (Double Δcт : ΔcтList) {
				Double ΔΔcт = Δcт - avgΔcтControl;
				ΔΔcтList.add(ΔΔcт);
				System.out.print("ΔΔcт: " + ΔΔcт + ", ");
				Double rqValue = rq(ΔΔcт);
				System.out.println("rqValue: " + rqValue);
				rqList.add(rqValue);
			}

			group.setΔcтList(ΔcтList);
			group.setΔΔcтList(ΔΔcтList);
			group.setRqList(rqList);

			Double avgΔΔcт = mean(ΔΔcтList);
			group.setMeanΔΔcт(avgΔΔcт);
			System.out.println("avgΔΔcт: " + avgΔΔcт);

			Double semΔΔcт = stderr(ΔΔcтList);
			group.setSemΔΔcт(semΔΔcт);
			System.out.println("semΔΔcт: " + semΔΔcт);

			Double meanRq = mean(rqList);
			group.setMeanRq(meanRq);
			System.out.println("avgRq: " + meanRq);

			Double semRq = stderr(rqList);
			group.setSemRq(semRq);
			System.out.println("semRq: " + semRq);								
		}
		System.out.println("Processed");

		return experiment;
	}




	private static ArrayList<Double> processΔcт(String groupName, String targetName, Experiment experiment) {		
		ExperimentDefinition experimentDefinition = experiment.getExperimentDefinition();
		Boolean isControl = groupName.equals(ExperimentDefinition.CONTROL_GROUP_NAME);

		ArrayList<String> sampleNameList;
		if (!isControl)
			sampleNameList = experimentDefinition.getSampleNameListForGroup(groupName);
		else
			sampleNameList = experimentDefinition.getSampleNameListForControl();						

		ArrayList<Double> ΔcтList = new ArrayList<>(sampleNameList.size());

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

	private static double mean(ArrayList<Double> list) {
		double tot = 0.0;
		for (Double i : list)
			tot += i;
		return tot / list.size();
	}
	private static double sdev(ArrayList<Double> list) {
		return Math.sqrt(variance(list));
	}
	private static Double stderr(ArrayList<Double> list) {
		return sdev(list) / Math.sqrt(list.size());
	}
	private static double variance(ArrayList<Double> list) {
		double mu = mean(list);
		double sumsq = 0.0;
		for (Double i : list)
			sumsq += sqr(mu - i);
		return sumsq / (list.size());
	}
	private static double sqr(double x) {
		return x * x;
	}
	private static Double rq(Double exponent) {
		return Math.pow(2, -exponent);
	}


}
