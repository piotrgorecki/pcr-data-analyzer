package com.pgorecki.pcr.appliedBiosystems;

import java.util.ArrayList;

public class Counterpart {

	private ArrayList<Well> wellList = new ArrayList<Well>();
	private String sampleName;
	
	public Counterpart(String sampleName) {
		super();
		this.sampleName = sampleName;
	}	
	
	public ArrayList<Well> getWellList() {
		return wellList;
	}
	public String getSampleName() {
		return sampleName;
	}
	
	public void add(Well well) {
		this.wellList.add(well);
	}

	
	
}
