package husacct.analyse.task;

import husacct.analyse.domain.AnalyseDomainService;
import husacct.analyse.domain.AnalyseDomainServiceImpl;
import husacct.analyse.task.analyser.ApplicationAnalyser;
import husacct.common.dto.AnalysedModuleDTO;
import husacct.common.dto.DependencyDTO;

public class AnalyseControlerServiceImpl implements AnalyseControlService{

	private ApplicationAnalyser analyserService; 
	private AnalyseDomainService domainService;
	private boolean isAnalysed = false;
	
	public AnalyseControlerServiceImpl(){
		this.domainService = new AnalyseDomainServiceImpl();
		this.analyserService = new ApplicationAnalyser();
	}
	
	@Override
	public void analyseApplication() {
		if(isAnalysed) domainService.clearModel();
		analyserService.analyseApplication();
		isAnalysed = true;
	}
	
	@Override
	public String[] getAvailableLanguages() {
		return analyserService.getAvailableLanguages();
	}
	
	@Override
	public AnalysedModuleDTO[] getRootModules() {
		return domainService.getRootModules();
	}
	

	@Override
	public AnalysedModuleDTO[] getChildModulesInModule(String from) {
		return domainService.getChildModulesInModule(from);
	}
	
	@Override
	public AnalysedModuleDTO[] getChildModulesInModule(String from, int depth) {
		return domainService.getChildModulesInModule(from, depth);
	}
	
	@Override
	public AnalysedModuleDTO getParentModuleForModule(String child) {
		return domainService.getParentModuleForModule(child);
	}
	
	@Override
	public DependencyDTO[] getDependencies(String from, String to) {
		return domainService.getDependencies(from, to);
	}

	@Override
	public DependencyDTO[] getDependencies(String from, String to, String[] dependencyFilter) {
		return domainService.getDependencies(from, to, dependencyFilter);
	}
	
	@Override
	public DependencyDTO[] getDependenciesFrom(String from) {
		return domainService.getDependenciesFrom(from);
	}

	@Override
	public DependencyDTO[] getDependenciesFrom(String from, String[] dependencyFilter) {
		return domainService.getDependenciesFrom(from, dependencyFilter);
	}

	@Override
	public DependencyDTO[] getDependenciesTo(String to) {
		return domainService.getDependenciesTo(to);
	}

	@Override
	public DependencyDTO[] getDependenciesTo(String to, String[] dependencyFilter) {
		return domainService.getDependenciesTo(to, dependencyFilter);
	}
}
