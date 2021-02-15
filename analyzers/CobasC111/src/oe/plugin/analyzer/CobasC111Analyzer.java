<<<<<<< HEAD
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

import static org.openelisglobal.common.services.PluginAnalyzerService.getInstance;

import java.util.ArrayList;
import java.util.List;

import org.openelisglobal.analyzerimport.analyzerreaders.AnalyzerLineInserter;
import org.openelisglobal.common.services.PluginAnalyzerService;
import org.openelisglobal.plugin.AnalyzerImporterPlugin;


public class CobasC111Analyzer implements AnalyzerImporterPlugin {

    public boolean connect(){
        List<PluginAnalyzerService.TestMapping> nameMappinng = new ArrayList<PluginAnalyzerService.TestMapping>();
        nameMappinng.add(new PluginAnalyzerService.TestMapping("GLU2", "Glucose"));
        nameMappinng.add(new PluginAnalyzerService.TestMapping("CREJ2", "Créatinine"));
        nameMappinng.add(new PluginAnalyzerService.TestMapping("ALTL", "Transaminases GPT (37°C)"));
        nameMappinng.add(new PluginAnalyzerService.TestMapping("ASTL", "Transaminases G0T (37°C)"));
        nameMappinng.add(new PluginAnalyzerService.TestMapping("CHOL2", "Cholestérol total"));
        nameMappinng.add(new PluginAnalyzerService.TestMapping("HDLC3", "Cholestérol HDL"));
        nameMappinng.add(new PluginAnalyzerService.TestMapping("TRIGL", "Triglycérides"));
        
         
        getInstance().addAnalyzerDatabaseParts("CobasC111Analyzer", "Plugin for Cobas C111 analyzer",nameMappinng);
        getInstance().registerAnalyzer(this);
        return true;
    }

    @Override
    public boolean isTargetAnalyzer(List<String> lines) {
    
    	if(getColumnsLine(lines)<0) return false;
    	 
    	return true;
    	
    }

    @Override
    public AnalyzerLineInserter getAnalyzerLineInserter() {
        return new CobasC111AnalyzerImplementation();
    }

	public int getColumnsLine(List<String> lines) {
		for(int k=0;k<lines.size();k++){
		if(lines.get(k).contains("Instr")&& lines.get(k).contains("Msg"))
		return k;
			
		}
		
		return -1;
	}
}
=======
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

import static org.openelisglobal.common.services.PluginAnalyzerService.getInstance;

import java.util.ArrayList;
import java.util.List;

import org.openelisglobal.analyzerimport.analyzerreaders.AnalyzerLineInserter;
import org.openelisglobal.common.services.PluginAnalyzerService;
import org.openelisglobal.plugin.AnalyzerImporterPlugin;


public class CobasC111Analyzer implements AnalyzerImporterPlugin {

    public boolean connect(){
        List<PluginAnalyzerService.TestMapping> nameMappinng = new ArrayList<PluginAnalyzerService.TestMapping>();
        nameMappinng.add(new PluginAnalyzerService.TestMapping("GLU2", "Glucose"));
        nameMappinng.add(new PluginAnalyzerService.TestMapping("CREJ2", "Créatinine"));
        nameMappinng.add(new PluginAnalyzerService.TestMapping("ALTL", "Transaminases GPT (37°C)"));
        nameMappinng.add(new PluginAnalyzerService.TestMapping("ASTL", "Transaminases G0T (37°C)"));
        nameMappinng.add(new PluginAnalyzerService.TestMapping("CHOL2", "Cholestérol total"));
        nameMappinng.add(new PluginAnalyzerService.TestMapping("HDLC3", "Cholestérol HDL"));
        nameMappinng.add(new PluginAnalyzerService.TestMapping("TRIGL", "Triglycérides"));
        
         
        getInstance().addAnalyzerDatabaseParts("CobasC111Analyzer", "Plugin for Cobas C111 analyzer",nameMappinng);
        getInstance().registerAnalyzer(this);
        return true;
    }

    @Override
    public boolean isTargetAnalyzer(List<String> lines) {
    
    	if(getColumnsLine(lines)<0) return false;
    	 
    	return true;
    	
    }

    @Override
    public AnalyzerLineInserter getAnalyzerLineInserter() {
        return new CobasC111AnalyzerImplementation();
    }

	public int getColumnsLine(List<String> lines) {
		for(int k=0;k<lines.size();k++){
		if(lines.get(k).contains("Instr")&& lines.get(k).contains("Msg"))
		return k;
			
		}
		
		return -1;
	}
}
>>>>>>> branch 'develop' of https://github.com/phassoa/openelisglobal-plugins.git
