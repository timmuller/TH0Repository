package analyse.domain.analyseservice;

import analyse.domain.analyseservice.dto.AnalysedModuleDTO;
import analyse.domain.analyseservice.dto.DependencyDTO;
import analyse.domain.stubs.AnalyseServiceStub;

public class AnalyseController implements AnalyseService{

	private AnalyseServiceStub stub;
	
	public AnalyseController(){
		stub = new AnalyseServiceStub();
	}
	
	
	@Override
	public void analyseApplication() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DependencyDTO getDependency(String from, String to) {		
		return stub.getDependency(from, to);
	}

	@Override
	public DependencyDTO getDependency(String from) {
		return stub.getDependency(from);
	}

	@Override
	public String[] getAvailableLanguages() {
		return stub.getAvailableLanguages();
	}

	@Override
	public AnalysedModuleDTO[] getModules() {
		return stub.getModules();
	}

	@Override
	public AnalysedModuleDTO[] getChildModulesInModule(String from) {
		return stub.getChildModulesInModule(from);
	}

	@Override
	public AnalysedModuleDTO[] getChildModulesInModule(String from, int depth) {
		return stub.getChildModulesInModule(from, depth);
	}

	@Override
	public AnalysedModuleDTO getParentModule(String child) {
		return stub.getParentModule(child);
	}

}
