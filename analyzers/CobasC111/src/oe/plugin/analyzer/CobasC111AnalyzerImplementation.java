/*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations under
 * the License.
 *
 * The Original Code is OpenELIS code.
 *
 * Copyright (C) ITECH, University of Washington, Seattle WA.  All Rights Reserved.
 */

package oe.plugin.analyzer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openelisglobal.analysis.service.AnalysisService;
import org.openelisglobal.analysis.valueholder.Analysis;
import org.openelisglobal.analyzer.service.AnalyzerService;
import org.openelisglobal.analyzer.valueholder.Analyzer;
import org.openelisglobal.analyzerimport.analyzerreaders.AnalyzerLineInserter;
import org.openelisglobal.analyzerresults.valueholder.AnalyzerResults;
import org.openelisglobal.common.services.StatusService;
import org.openelisglobal.common.util.ConfigurationProperties;
import org.openelisglobal.common.util.ConfigurationProperties.Property;
import org.openelisglobal.common.util.DateUtil;
import org.openelisglobal.sample.service.SampleService;
import org.openelisglobal.spring.util.SpringContext;
import org.openelisglobal.test.service.TestService;
import org.openelisglobal.test.valueholder.Test;


public class CobasC111AnalyzerImplementation extends AnalyzerLineInserter {

	static String ANALYZER_ID;
	//static String DATE_PATTERN = "yyyyMMddHH:mm";
	static String DATE_PATTERN = "yyyyMMdd";

	private static final String CONTROL_ACCESSION_PREFIX = "PCC";
	//private static final String[] units = new String[100];
	//private static final int[] scaleIndex = new int[100];
	private static final String DELIMITER = ";";
    private final String accession_number_prefix = ConfigurationProperties.getInstance().getPropertyValue(Property.ACCESSION_NUMBER_PREFIX);
	double result = Double.NaN;
	String line;
	String[] data;
	String validStatusId = StatusService.getInstance().getStatusID(StatusService.AnalysisStatus.Finalized);
    	AnalysisService analysisDao =  SpringContext.getBean(AnalysisService.class);
        


	static HashMap<String, Test> testNameMap = new HashMap<>();
	static HashMap<String, String> testUnitMap = new HashMap<>();

	static{

		testNameMap.put("GLU2", SpringContext.getBean(TestService.class).getTestByName("Glucose"));
		testNameMap.put("CREJ2", SpringContext.getBean(TestService.class).getTestByName("Créatinine"));
		testNameMap.put("ALTL", SpringContext.getBean(TestService.class).getTestByName("Transaminases GPT (37°C)"));
		testNameMap.put("ASTL", SpringContext.getBean(TestService.class).getTestByName("Transaminases G0T (37°C)"));
        testNameMap.put("CHOL2", SpringContext.getBean(TestService.class).getTestByName("Cholestérol total"));
        testNameMap.put("HDLC3", SpringContext.getBean(TestService.class).getTestByName("Cholestérol HDL"));
        testNameMap.put("TRIGL", SpringContext.getBean(TestService.class).getTestByName("Triglycérides"));
            
            
		System.out.println(testNameMap);

		AnalyzerService analyzerService = SpringContext.getBean(AnalyzerService.class);
		Analyzer analyzer = analyzerService.getAnalyzerByName("CobasC111Analyzer");
		ANALYZER_ID = analyzer.getId();


	}


	static{

		testUnitMap.put("GLU2", "/1|10^3/uL");
		testUnitMap.put("CREJ2", "/1|10^6/uL");
		testUnitMap.put("ALTL", "/1|g/dL");

 		testUnitMap.put("ASTL", "/1|g/dL");
        testUnitMap.put("CHOL2", "/1|g/L");
        testUnitMap.put("HDLC3", "/1|g/L");
        testUnitMap.put("TRIGL", "/1|g/L");
        
		System.out.println(testUnitMap);



	}

	/* (non-Javadoc)
	 * @see org.openelisglobal.analyzerimport.analyzerreaders.AnalyzerLineInserter#insert(java.util.List, java.lang.String)
	 */
	@Override
	public boolean insert(List<String> lines, String currentUserId) {
		List<AnalyzerResults> results = new ArrayList<>();

		for (Integer j = 1; j < lines.size(); j++) {
			System.out.println("processing line #: "  + j);
			line = lines.get(j);
			data = line.split(DELIMITER);

			if (line.length() == 0 || data.length == 0) {
				continue;
			}
			//String dateTime = "";

			String currentAccessionNumber = data [10].replace("\"", "").trim();
			//--------------------------
        	AnalyzerResults aResult = new AnalyzerResults();
        	
            SampleService sampleServ=SpringContext.getBean(SampleService.class);
            System.out.println("labno: "  + currentAccessionNumber);
        	                  	
        	if (currentAccessionNumber.startsWith(accession_number_prefix)||sampleServ.getSampleByAccessionNumber(aResult.getAccessionNumber())!=null ) {         
        		         
        		
        	        String testKey = data [8].replace("\"", "").trim();
                    String date = data [4].replace("\"", "");                       
                                
                    aResult.setTestId(testNameMap.get(testKey).getId());	        		
                    aResult.setTestName(testNameMap.get(testKey).getName());
              System.out.println("Result: "  + data [12].replace("\"", "").trim());       
                    aResult.setResult(data [12].replace("\"", "").trim());
                    aResult.setAnalyzerId(ANALYZER_ID);
                    aResult.setUnits(data [13].replace("\"", ""));
                    aResult.setAccessionNumber(data [10].replace("\"", "").trim());
               //   aResult.setReadOnly(CheckReadOnly (testKey));
                    aResult.setIsControl(CheckControl (currentAccessionNumber));
			        aResult.setCompleteDate(getTimestampFromDate(date));
                              

	           	System.out.println("***" + aResult.getAccessionNumber() + " " + aResult.getCompleteDate() + " " + aResult.getResult());
	            List<Analysis> analyses=analysisDao.getAnalysisByAccessionAndTestId(aResult.getAccessionNumber(), aResult.getTestId());
				  
	           	
	           	for(Analysis analysis :analyses) {
						if(!analysis.getStatusId().equals(validStatusId)){
							results.add(aResult);	
						}else break;
                    
					}
			}
		}
		return persistImport(currentUserId, results);
	}

	
	private boolean CheckControl (String AccessionPrefix){

		boolean IsControl= false;

		if (AccessionPrefix.startsWith(CONTROL_ACCESSION_PREFIX)) {
			IsControl = true;
		}
		return IsControl;

	}

	/*
 private void addValueToResults(List<AnalyzerResults> resultList, AnalyzerResults result){
		resultList.add(result);

		AnalyzerResults resultFromDB = readerUtil.createAnalyzerResultFromDB(result);
		if(resultFromDB != null){
			resultList.add(resultFromDB);
		}
 }
	 */
	private Timestamp getTimestampFromDate(String dateTime) {
		return DateUtil.convertStringDateToTimestampWithPattern(dateTime, DATE_PATTERN);
	}

	@Override
	public String getError() {
		return "Cobas C111 analyzer unable to write to database";
	}
}
