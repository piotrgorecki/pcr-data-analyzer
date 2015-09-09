package com.pgorecki.pcr.appliedBiosystems;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class XLSReader {

	private String xlsPath;

	public String getXlsPath() {
		return xlsPath;
	}

	public void setXlsPath(String xlsPath) {
		this.xlsPath = xlsPath;
	}

	public XLSReader(String xlsPath) {
		super();
		this.xlsPath = xlsPath;
	}
	
	public Experiment parseExperiment(String sheetName, ExperimentDefinition experimentDefinition) {
		Experiment experiment = new Experiment(experimentDefinition);
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(xlsPath);
			Workbook workbook = new HSSFWorkbook(fis);
			Sheet sheet = workbook.getSheet(sheetName);			
			Iterator<Row> rowIterator = sheet.iterator();
			
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				
				if (row.getLastCellNum() <= 2 || row.getCell(10).getCellType() == Cell.CELL_TYPE_STRING)
					continue;

				Iterator<Cell> cellIterator = row.cellIterator();
					
				String well = "";
				String sampleName = "";
				String targetName = "";		
				double cт = -1.0;				
				
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();										
														
					if (cell.getColumnIndex() == 0)
						well = cell.getStringCellValue();
					else if (cell.getColumnIndex() == 1 && cell.getCellType() == Cell.CELL_TYPE_STRING)
						sampleName = cell.getStringCellValue();
					else if (cell.getColumnIndex() == 2 && cell.getCellType() == Cell.CELL_TYPE_STRING)
						targetName = cell.getStringCellValue();
					else if (cell.getColumnIndex() == 9 && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						cт = cell.getNumericCellValue();									
						break;
					}
				}

				experiment.addSample(well, sampleName, targetName, cт);
				
			}
			
			workbook.close();
			fis.close();
			
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return experiment;
	}
	
}
