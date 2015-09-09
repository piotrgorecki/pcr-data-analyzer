package com.pgorecki.pcr.appliedBiosystems;

public class TargetName {
	
	private String name;
	private Boolean isReference;
	
	public TargetName(String name, Boolean isReference) {
		super();
		this.name = name;
		this.isReference = isReference;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getIsReference() {
		return isReference;
	}
	public void setIsReference(Boolean isReference) {
		this.isReference = isReference;
	}
}
