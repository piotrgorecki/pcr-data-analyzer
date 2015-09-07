package com.pgorecki.pcr;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.pgorecki.*;

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
	
	public List<Sample> getSampleList(String sheetName) {
		List<Sample> sampleList = new ArrayList<Sample>();
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(xlsPath);
			Workbook workbook = new HSSFWorkbook(fis);
			Sheet sheet = workbook.getSheet(sheetName);			
			Iterator<Row> rowIterator = sheet.iterator();
			
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				
				if (row.getLastCellNum() <= 2 || row.getCell(3).getCellType() == Cell.CELL_TYPE_STRING)
					continue;
				
				Iterator<Cell> cellIterator = row.cellIterator();
					
				Sample sample = new Sample();
				
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
														
					if (cell.getColumnIndex() == 0)
						sample.setSampleName(cell.getStringCellValue());
					else if (cell.getColumnIndex() == 1 && cell.getCellType() == Cell.CELL_TYPE_STRING)
						sample.setTargetName(cell.getStringCellValue());
					else if (cell.getColumnIndex() == 2 && cell.getCellType() == Cell.CELL_TYPE_STRING)
						sample.setTask(cell.getStringCellValue());
					else if (cell.getColumnIndex() == 3 && cell.getCellType() == Cell.CELL_TYPE_STRING)
						sample.setRq(cell.getNumericCellValue());
					else if (cell.getColumnIndex() == 4 && cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
						sample.setRqMin(cell.getNumericCellValue());
					else if (cell.getColumnIndex() == 5 && cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
						sample.setRqMax(cell.getNumericCellValue());
					else if (cell.getColumnIndex() == 6 && cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
						sample.setCтMean(cell.getNumericCellValue());
					else if (cell.getColumnIndex() == 7 && cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
						sample.setΔcтMean(cell.getNumericCellValue());
					else if (cell.getColumnIndex() == 8 && cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
						sample.setΔcтSE(cell.getNumericCellValue());
					else if (cell.getColumnIndex() == 9 && cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
						sample.setΔΔcт(cell.getNumericCellValue());
					
				}
				
				sampleList.add(sample);
				
			}
			
			workbook.close();
			fis.close();
			
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return sampleList;
	}
	
		
}
