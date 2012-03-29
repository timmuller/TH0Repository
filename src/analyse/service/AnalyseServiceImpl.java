package analyse.service;

import analyse.dto.DependencyDTO;
import analyse.dto.PackageDTO;

import java.util.ArrayList;
import java.util.List;

public class AnalyseServiceImpl implements AnalyseService {

	SetupDTOs setupDTOs = new SetupDTOs();
	
	@Override
	public DependencyDTO getDependency(String from, String to) {
		DependencyDTO dDTO = new DependencyDTO();
		dDTO.setFrom("class1");
		dDTO.setTo("Class2");
		dDTO.setType("Class");
		dDTO.setLineNumber(15);
		return dDTO;
	}
	
	@Override
	public List<DependencyDTO> getDependency(String regexin) {
		List<DependencyDTO> dependencyDTOs = new ArrayList<DependencyDTO>();
		dependencyDTOs.add(setupDTOs.dependencyDTO1);
		dependencyDTOs.add(setupDTOs.dependencyDTO2);
		
		return dependencyDTOs;
	}

	@Override
	public List<PackageDTO> getPackages(String packageString) {
		//returns all packages
		List<PackageDTO> packagesDTOs = new ArrayList<PackageDTO>();
		packagesDTOs.add(setupDTOs.package1);
		packagesDTOs.add(setupDTOs.package2);
		return packagesDTOs;
		
	}

	@Override
	public List<String> getAvailableLanguages() {
		List<String> languages = new ArrayList<String>();
		languages.add("Java");
		languages.add("C#");
		return languages;
	}
}
