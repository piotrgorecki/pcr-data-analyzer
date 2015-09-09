package com.pgorecki.pcr.appliedBiosystems;

public class Well {

	private String id;
	private double cт;
	
	public Well(String id, double cт) {
		super();
		this.id = id;
		this.cт = cт;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getCт() {
		return cт;
	}
	public void setCт(double cт) {
		this.cт = cт;
	}
		
			
}
