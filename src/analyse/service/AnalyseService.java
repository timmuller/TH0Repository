package analyse.service;

import analyse.dto.DependencyDTO;
import analyse.dto.PackageDTO;

import java.util.List;

public interface AnalyseService {

	public DependencyDTO getDependency(String from, String to);
	
	public List<DependencyDTO> getDependency(String regexin);
	
	public List<PackageDTO> getPackages(String packageString);
	
	public List<String> getAvailableLanguages();
}
