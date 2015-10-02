package com.pgorecki.pcr.appliedBiosystems;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Reporter {

	private Experiment experiment;
	private Sheet sheet;
	private Row row;
	private int r = -1;	
	private int c = -1;
	private Cell cc;
	
	
	public Reporter(Experiment experiment) {
		this.experiment = experiment;
	}
	
	@SuppressWarnings("resource")
	public void makeReport(String path) {
		
		try {			
			Workbook wb = new XSSFWorkbook();								
			this.sheet = wb.createSheet("results");
			
			// Experiment information
			nextRow();
			firstCell().setCellValue("Test Name:");
			nextCell().setCellValue(experiment.getName());
			nextRow();
		    
		    // Table header
			nextRow(); 
			firstCell().setCellValue("Group name");
			nextCell().setCellValue("Target Name");			
			nextCell().setCellValue("Sample Name");
			nextCell().setCellValue("Δcт");
			nextCell().setCellValue("ΔΔcт");
			nextCell().setCellValue("Rq");
			nextCell().setCellValue("Mean ΔΔcт");
			nextCell().setCellValue("Sem ΔΔcт");
			nextCell().setCellValue("Mean Rq");
			nextCell().setCellValue("Sem Rq");
			
			
		    System.out.println("==== Generate XLS ====");
		    
		    // Data
		    for (Group group : this.experiment.getGroupList()) {
			    System.out.println("group: " + group.getName());
			    
			    
			    if (group.getΔcтList() != null) {
			    
			    	Iterator<String> sampleNameList = group.getSampleNameList().iterator();
			    	Iterator<Double> ΔcтList = group.getΔcтList().iterator();
			    	Iterator<Double> ΔΔcтList = group.getΔΔcтList().iterator();
			    	Iterator<Double> rqList = group.getRqList().iterator();
			    	
			    	nextRow();
			    	firstCell().setCellValue(group.getName());
			    	nextCell().setCellValue(group.getTargetName());
			    	rememberCell();

			    	boolean isSummaryRow = true;
			    	while (sampleNameList.hasNext()) {			    		
			    		nextCell().setCellValue(sampleNameList.next());
			    		nextCell().setCellValue(ΔcтList.next());
			    		nextCell().setCellValue(ΔΔcтList.next());
			    		nextCell().setCellValue(rqList.next());
			    		
			    		if (isSummaryRow) {
			    			nextCell().setCellValue(group.getMeanΔΔcт());
			    			nextCell().setCellValue(group.getSemΔΔcт());
			    			nextCell().setCellValue(group.getMeanRq());
			    			nextCell().setCellValue(group.getSemRq());
			    			isSummaryRow = false;
			    		}
			    		
			    		nextRow();
			    		backToCell();
			    	}
			    
			    }
			    

		    }
		    
			
			// save
			FileOutputStream fileOut = new FileOutputStream(path);
			wb.write(fileOut);
			fileOut.close();
			
			
		} catch (EncryptedDocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	private void nextRow() {
		this.row = this.sheet.createRow((short)(this.r += 1));
	}	
	
	private Cell nextCell() {
		return this.row.createCell(this.c += 1);
	}
	
	private Cell firstCell() {
		this.c = 0;
		return this.row.createCell(this.c);
	}	
	
	private void rememberCell() {
		this.cc = this.row.getCell(this.c);
	}
	
	private Cell backToCell() {
		this.c = this.cc.getColumnIndex();
		return this.cc;
	}
	
	
	
}
