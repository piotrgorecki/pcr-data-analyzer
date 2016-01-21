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

	public Experiment parseExperiment(String sheetName, Experiment experiment) throws Exception  {		
		FileInputStream fis = null;		
		Workbook workbook = null;

		try {			
			fis = new FileInputStream(xlsPath);
			workbook = new HSSFWorkbook(fis);
			Sheet sheet = workbook.getSheet(sheetName);			
			Iterator<Row> rowIterator = sheet.iterator();

			try {
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

						// well
						if (cell.getColumnIndex() == 0)
							well = cell.getStringCellValue();
						
						// sample name
						else if (cell.getColumnIndex() == 1)
							if (cell.getCellType() == Cell.CELL_TYPE_STRING)
								sampleName = cell.getStringCellValue();
							else {
								double cellValue = -1;
								try {
									cellValue = cell.getNumericCellValue();
									sampleName = String.valueOf((int) Math.round(cellValue));
								} catch (Exception e) {									
									e.printStackTrace();
									throw new Exception("Unable cast sampleName: \"" + cellValue + "\"to integer");
								}
							}							

						// target name
						else if (cell.getColumnIndex() == 2) 
							if (cell.getCellType() == Cell.CELL_TYPE_STRING)
								targetName = cell.getStringCellValue();
							else {
								double cellValue = -1;
								try {
									cellValue = cell.getNumericCellValue();
									targetName = String.valueOf((int) Math.round(cellValue));
								} catch (Exception e) {
									e.printStackTrace();
									throw new Exception("Unable cast sampleName: \"" + cellValue + "\"to integer");
								}
							}

						// ct
						else if (cell.getColumnIndex() == 9 && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
							cт = cell.getNumericCellValue();									
							break;
						}
					}

					experiment.addSample(well, sampleName, targetName, cт);
				} // while
				
			// try of parse the file
			} catch (Exception ex) { 
				throw new Exception("Cannot parse " + sheetName + " sheet.\n" + ex.getMessage());
			}

		// try of open and read the file
		} catch (FileNotFoundException ex) {
			throw new Exception("File \"" + xlsPath + "\" not found");
		} catch (IOException ex) {
			throw new Exception("Cannot read " + sheetName + " sheet. It is an correct XLS file?\n" + ex.getMessage());
		} finally {
			if (workbook != null) workbook.close();
			if (fis != null) fis.close();
		}

		return experiment;
	}

}
