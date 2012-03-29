package com.hu.husacct.analyse.domain.stubs;

import com.hu.husacct.analyse.domain.analyseservice.AnalyseService;
import com.hu.husacct.analyse.domain.analyseservice.dto.AnalysedModuleDTO;
import com.hu.husacct.analyse.domain.analyseservice.dto.DependencyDTO;

public class AnalyseServiceStub implements AnalyseService{

	@Override
	public void analyseApplication() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DependencyDTO getDependency(String from, String to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DependencyDTO getDependency(String from) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getAvailableLanguages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AnalysedModuleDTO getModules() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AnalysedModuleDTO getChildModulesInModule(String from) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AnalysedModuleDTO getChildModulesInModule(String from, int depth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AnalysedModuleDTO getParentModule(String child) {
		// TODO Auto-generated method stub
		return null;
	}

}
