package com.pgorecki;

import java.util.*;

import com.pgorecki.pcr.XLSReader;

public class PCR {

	public static void main(String[] args) {

		
		XLSReader xls = new XLSReader("/home/piotr/workspace/20150904PBG_data.xls");
		List<Sample> sampleList = xls.getSampleList("Technical Analysis Result");

		for (Sample s : sampleList)
			System.out.println(s.toString());
	
		
	}

}
