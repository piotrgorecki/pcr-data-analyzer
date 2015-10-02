package com.pgorecki.pcr.appliedBiosystems;

import java.util.ArrayList;
import java.util.HashMap;

public class ExperimentDefinition {

	final public static String CONTROL_GROUP_NAME = "control";

	private String experimentName;
	private String reference;
	private ArrayList<String> controlGroup = new ArrayList<String>();
	private ArrayList<HashMap<String, String[]>> groupList = new ArrayList<>();
	
	public ExperimentDefinition(String name, String reference, String control) {
		super();
		this.experimentName = name;
		this.reference = reference;
		for (String el : control.split(", ?"))
			this.controlGroup.add(el);
	}
	
	public void addGroup(String name, String targetNames) {
		String[] splitted = targetNames.split(", ?");
		HashMap<String, String[]> map = new HashMap<String, String[]>();
		map.put(name, splitted);
		this.groupList.add(map);
	}
		
	public String getExperimentName() {
		return this.experimentName;
	}
	public String getReference() {
		return this.reference;
	}
	public ArrayList<String> getControlGroup() {
		return this.controlGroup;
	}
	public ArrayList<HashMap<String, String[]>> getGroupList() {
		return groupList;
	}

	public Boolean isGroupForSampleName(String sampleName) {
		for (HashMap<String, String[]> pair : this.groupList)
			for (String[] v : pair.values())
				for (String s : v)
					if (s.equals(sampleName))
						return true;

		if (this.controlGroup.contains(sampleName))
			return true;

		return false;
	}

	public String getGroupNameForSampleName(String sampleName) {

		for (HashMap<String, String[]> pair : this.groupList)
			for (String[] v : pair.values())
				for (String s : v)
					if (s.equals(sampleName))
						return pair.keySet().iterator().next();


		for (String name : this.controlGroup)
			if (name.equals(sampleName))
				return ExperimentDefinition.CONTROL_GROUP_NAME;

		return "";
	}	

	public Boolean isTargetNameReference(String targetName) {
		return this.reference.equals(targetName);
	}

	public Boolean isControlGroup(String sampleName) {
		return this.getControlGroup().contains(sampleName);
	}

	public String[] getSampleNameListForGroup(String groupName) {
		for (HashMap<String, String[]> pair : this.groupList)
			if (pair.keySet().contains(groupName))
				return pair.get(groupName);

		return new String[] {};													
	}

	public String[] getSampleNameListForControl() {
		return this.controlGroup.toArray(new String[this.controlGroup.size()]);
	}

}
