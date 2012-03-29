package com.hu.husacct.analyse.domain.analyseservice;

import com.hu.husacct.analyse.domain.analyseservice.dto.AnalysedModuleDTO;
import com.hu.husacct.analyse.domain.analyseservice.dto.DependencyDTO;

public interface AnalyseService {
	
	public void analyseApplication();
	
	public DependencyDTO getDependency(String from, String to);
	public DependencyDTO getDependency(String from);
	
	public String[] getAvailableLanguages();
	
	public AnalysedModuleDTO getModules();
	public AnalysedModuleDTO getChildModulesInModule(String from); 
	public AnalysedModuleDTO getChildModulesInModule(String from, int depth);
	public AnalysedModuleDTO getParentModule(String child);
	
}
