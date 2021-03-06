package husacct.analyse;

import javax.swing.JInternalFrame;
import husacct.analyse.presentation.AnalyseInternalFrame;
import husacct.analyse.presentation.AnalyseUIController;
import husacct.analyse.task.AnalyseControlService;
import husacct.analyse.task.AnalyseControlerServiceImpl;
import husacct.common.dto.AnalysedModuleDTO;
import husacct.common.dto.DependencyDTO;

public class AnalyseServiceImpl implements IAnalyseService{

	private AnalyseControlService service = new AnalyseControlerServiceImpl();
	private AnalyseServiceStub stub;
	private AnalyseUIController analyseGui;
	private boolean isAnalysed = false;
	
	public AnalyseServiceImpl(){
		stub = new AnalyseServiceStub();
	}

	@Override
	public String[] getAvailableLanguages() {
		return service.getAvailableLanguages();
	}

	@Override
	public void analyseApplication() {
		service.analyseApplication();
		this.isAnalysed = true;
	}
	
	@Override
	public boolean isAnalysed() {
		return this.isAnalysed;
	}
	
	@Override
	public JInternalFrame getJInternalFrame() {
		this.analyseGui = new AnalyseUIController();
		return analyseGui.getAnalysedCodeFrame();
	}
	
	@Override
	public AnalysedModuleDTO[] getRootModules() {
		return service.getRootModules();
	}
	

	@Override
	public AnalysedModuleDTO[] getChildModulesInModule(String from) {
		return service.getChildModulesInModule(from);
	}

	@Override
	public AnalysedModuleDTO[] getChildModulesInModule(String from, int depth) {
		return service.getChildModulesInModule(from, depth);
	}

	@Override
	public AnalysedModuleDTO getParentModuleForModule(String child) {
		return service.getParentModuleForModule(child);
	}

	@Override
	public DependencyDTO[] getDependencies(String from, String to) {
		return service.getDependencies(from, to);
	}
	
	@Override
	public DependencyDTO[] getDependencies(String from, String to, String[] dependencyFilter){
		return service.getDependencies(from, to, dependencyFilter);
	}

	@Override
	public DependencyDTO[] getDependenciesFrom(String from) {
		return service.getDependenciesFrom(from);
	}
	
	@Override
	public DependencyDTO[] getDependenciesFrom(String from, String[] dependencyFilter){
		return service.getDependenciesFrom(from, dependencyFilter);
	}
	
	@Override
	public DependencyDTO[] getDependenciesTo(String to){
		return service.getDependenciesTo(to);
	}

	@Override
	public DependencyDTO[] getDependenciesTo(String to, String[] dependencyFilter){
		return service.getDependenciesTo(to, dependencyFilter);
	}
	
	
}
