package husacct.analyse;

import javax.swing.JInternalFrame;

import husacct.common.dto.AnalysedModuleDTO;
import husacct.common.dto.DependencyDTO;

public interface IAnalyseService {
	
	public void analyseApplication();
	public boolean isAnalysed();
	
	public JInternalFrame getJInternalFrame();
	
	public DependencyDTO[] getDependencies(String from, String to);
	public DependencyDTO[] getDependenciesFrom(String from);
	public DependencyDTO[] getDependenciesTo(String to);
	
	public DependencyDTO[] getDependencies(String from, String to, String[] dependencyFilter);
	public DependencyDTO[] getDependenciesFrom(String from, String[] dependencyFilter);
	public DependencyDTO[] getDependenciesTo(String to, String[] dependencyFilter);
	
	public String[] getAvailableLanguages();
	
	public AnalysedModuleDTO[] getRootModules();
	public AnalysedModuleDTO[] getChildModulesInModule(String from); 
	public AnalysedModuleDTO[] getChildModulesInModule(String from, int depth);
	public AnalysedModuleDTO getParentModuleForModule(String child);
	
}
