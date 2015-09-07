package com.pgorecki;

public class Sample {

	private String sampleName;	
	private String targetName;	
	private	String task;
	private double rq;
	private double rqMin;
	private double rqMax;
	private double cтMean;
	private double ΔcтMean;
	private double ΔcтSE;
	private double ΔΔcт;
	
	public String getSampleName() {
		return sampleName;
	}
	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public double getRq() {
		return rq;
	}
	public void setRq(double rq) {
		this.rq = rq;
	}
	public double getRqMin() {
		return rqMin;
	}
	public void setRqMin(double rqMin) {
		this.rqMin = rqMin;
	}
	public double getRqMax() {
		return rqMax;
	}
	public void setRqMax(double rqMax) {
		this.rqMax = rqMax;
	}
	public double getCтMean() {
		return cтMean;
	}
	public void setCтMean(double cтMean) {
		this.cтMean = cтMean;
	}
	public double getΔcтMean() {
		return ΔcтMean;
	}
	public void setΔcтMean(double δcтMean) {
		this.ΔcтMean = δcтMean;
	}
	public double getΔcтSE() {
		return ΔcтSE;
	}
	public void setΔcтSE(double δcтSE) {
		this.ΔcтSE = δcтSE;
	}
	public double getΔΔcт() {
		return ΔΔcт;
	}
	public void setΔΔcт(double δΔcт) {
		this.ΔΔcт = δΔcт;
	}
	
	
	public Sample() {
		super();
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.sampleName + "; ");
		builder.append(this.targetName + "; ");
		builder.append(this.task + "; ");
		builder.append(this.rq + "; ");
		builder.append(this.rqMin + "; ");
		builder.append(this.rqMax + "; ");
		builder.append(this.cтMean + "; ");
		builder.append(this.ΔcтMean + "; ");
		builder.append(this.ΔcтSE + "; ");
		builder.append(this.ΔΔcт + "; ");
	
		return builder.toString();
	}
	
	
	
	
}
