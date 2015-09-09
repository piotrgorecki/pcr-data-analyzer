package com.pgorecki.pcr.appliedBiosystems;

import java.util.ArrayList;

public class Experiment {
	
	private ArrayList<Group> groupList;	
	private ExperimentDefinition experimentDefinition;
	private String name;
	
	public Experiment(ExperimentDefinition experimentDefinition) {
		super();
		this.groupList = new ArrayList<Group>();		
		this.experimentDefinition = experimentDefinition;
		this.name = experimentDefinition.getExperimentName();
	}

	public ArrayList<Group> getGroupList() {
		return groupList;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void addSample(String wellId, String sampleName, String targetName, Double cт) {
		
		if (this.experimentDefinition.isGroupForSampleName(sampleName)) {
			String groupName = this.experimentDefinition.getGroupNameForSampleName(sampleName);
			
			Group group;
			
			if (groupExist(groupName, targetName)) {					
				group = getGroup(groupName, targetName);
			}
			else {
				Boolean isReference = this.experimentDefinition.isTargetNameReference(targetName);
				Boolean isControl = this.experimentDefinition.isControllGroup(sampleName);
				TargetName oTargetName = new TargetName(targetName, isReference);
				group = new Group(groupName, isControl, oTargetName);
				this.groupList.add(group);
			}
			
			Well well = new Well(wellId, cт);
			group.addSample(well, sampleName);					
		}							
		
	}
	
	
	
	private Boolean groupExist(String sampleName, String targetName) {
		for (Group group : this.groupList)
			if (group.getName().equals(sampleName) && group.getTargetName().equals(targetName))
				return true;
		return false;
	}
	
	public Group getGroup(String groupName, String targetName) {
		for (Group group : this.groupList)
			if (group.getName().equals(groupName) && group.getTargetName().equals(targetName))
				return group;
		return null;
	}
	


}
