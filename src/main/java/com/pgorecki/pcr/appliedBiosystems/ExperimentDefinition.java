package com.pgorecki.pcr.appliedBiosystems;

import java.util.ArrayList;
import java.util.HashMap;

public class ExperimentDefinition {

	final public static String CONTROL_GROUP_NAME = "control";

	private String experimentName;
	private String reference;
	private ArrayList<String> controlGroupList = new ArrayList<>();
	private ArrayList<HashMap<String, ArrayList<String>>> groupList = new ArrayList<>();
	
	public ExperimentDefinition(String name, String reference, String control) {
		super();
		this.experimentName = name;
		this.reference = reference;
		for (String el : control.split(", ?"))
			this.controlGroupList.add(el);
	}
	
	public void addGroup(String name, String targetNames) {
		ArrayList<String> splitted = new ArrayList<>();
		for (String targetName : targetNames.split(", ?"))
			splitted.add(targetName);
		
		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		map.put(name, splitted);
		this.groupList.add(map);				
	}
		
	public String getExperimentName() {
		return this.experimentName;
	}
	public String getReference() {
		return this.reference;
	}
	public ArrayList<String> getControlGroupList() {
		return this.controlGroupList;
	}
	public ArrayList<HashMap<String, ArrayList<String>>> getGroupList() {
		return groupList;
	}

	public Boolean isGroupForSampleName(String sampleName) {
		for (HashMap<String, ArrayList<String>> pair : this.groupList)
			for (ArrayList<String> v : pair.values())
				for (String s : v)
					if (s.equals(sampleName))
						return true;

		if (this.controlGroupList.contains(sampleName))
			return true;

		return false;
	}

	public String getGroupNameForSampleName(String sampleName) {

		for (HashMap<String, ArrayList<String>> pair : this.groupList)
			for (ArrayList<String> v : pair.values())
				for (String s : v)
					if (s.equals(sampleName))
						return pair.keySet().iterator().next();


		for (String name : this.controlGroupList)
			if (name.equals(sampleName))
				return ExperimentDefinition.CONTROL_GROUP_NAME;

		return "";
	}	

	public Boolean isTargetNameReference(String targetName) {
		return this.reference.equals(targetName);
	}

	public Boolean isControlGroup(String sampleName) {
		return this.getControlGroupList().contains(sampleName);
	}

	public ArrayList<String> getSampleNameListForGroup(String groupName) {
		for (HashMap<String, ArrayList<String>> pair : this.groupList)
			if (pair.keySet().contains(groupName))
				return pair.get(groupName);

		return new ArrayList<String>();													
	}

	public ArrayList<String> getSampleNameListForControl() {
		return this.controlGroupList;
	}

}
