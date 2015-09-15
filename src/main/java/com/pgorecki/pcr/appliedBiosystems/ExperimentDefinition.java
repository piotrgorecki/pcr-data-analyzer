package com.pgorecki.pcr.appliedBiosystems;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ExperimentDefinition {

	final public static String CONTROL_GROUP_NAME = "controll";

	private String experimentName;
	private String reference;
	private ArrayList<String> controlGroup = new ArrayList<String>();
	private ArrayList<HashMap<String, String[]>> groupList = new ArrayList<>();

	public ExperimentDefinition(String definition) {
		super();
		readDefinition(new Scanner(definition));		
	}

	public ExperimentDefinition(Path path) {
		super();
		try {
			readDefinition(new Scanner(path));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}


	public String getExperimentName() {
		return this.experimentName;
	}
	public String getReference() {
		return this.reference;
	}
	public ArrayList<String> getControllGroup() {
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

	public Boolean isControllGroup(String sampleName) {
		return this.getControllGroup().contains(sampleName);
	}

	public String[] getSampleNameListForGroup(String groupName) {
		for (HashMap<String, String[]> pair : this.groupList)
			if (pair.keySet().contains(groupName))
				return pair.get(groupName);

		return new String[] {};													
	}

	public String[] getSampleNameListForControll() {
		return this.controlGroup.toArray(new String[this.controlGroup.size()]);
	}




	private void readDefinition(Scanner scanner) {	
		while (scanner.hasNext()) {
			String line = scanner.nextLine();	

			if (line.matches("experiment: ?.*"))
				this.experimentName = line.substring(line.indexOf(":") + 1).trim();
			else if (line.matches("reference: ?.*"))
				this.reference = line.substring(line.indexOf(":") +1).trim();
			else if (line.matches("controll: ?.*" ))					
				for (String sampleName : line.substring(line.indexOf(":") + 1).trim().split(", ?"))
					this.controlGroup.add(sampleName);
			else if (line.matches(".*: ?.*")) {
				String name = line.substring(0, line.indexOf(":")).trim();
				String[] splitted = line.substring(line.indexOf(":") + 1).trim().split(", ?");
				HashMap<String, String[]> map = new HashMap<String, String[]>();
				map.put(name, splitted);
				this.groupList.add(map);
			}

		}
		scanner.close();
	}

}
