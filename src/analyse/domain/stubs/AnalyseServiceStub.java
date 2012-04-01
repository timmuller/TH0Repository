package analyse.domain.stubs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import analyse.domain.analyseservice.AnalyseService;
import analyse.domain.analyseservice.dto.AnalysedModuleDTO;
import analyse.domain.analyseservice.dto.DependencyDTO;

public class AnalyseServiceStub implements AnalyseService{

	private HashMap<String, ArrayList<Object>> analysed;
	
	
	public AnalyseServiceStub(){
				
		ArrayList<AnalysedModuleDTO> foursquareSub = new ArrayList<AnalysedModuleDTO>();
		foursquareSub.add(new AnalysedModuleDTO("domain.locationbased.foursquare.Account", "Account"));
		foursquareSub.add(new AnalysedModuleDTO("domain.locationbased.foursquare.Friends", "Friends"));
		foursquareSub.add(new AnalysedModuleDTO("domain.locationbased.foursquare.Map", "Map"));
		foursquareSub.add(new AnalysedModuleDTO("domain.locationbased.foursquare.History", "History"));
		
		ArrayList<AnalysedModuleDTO> latitudeSub = new ArrayList<AnalysedModuleDTO>();
		latitudeSub.add(new AnalysedModuleDTO("domain.locationbased.latitude.Account", "Account"));
		latitudeSub.add(new AnalysedModuleDTO("domain.locationbased.latitude.Friends", "Friends"));
		latitudeSub.add(new AnalysedModuleDTO("domain.locationbased.latitude.Map", "Map"));		
		
		ArrayList<AnalysedModuleDTO> locationbasedSub = new ArrayList<AnalysedModuleDTO>();
		locationbasedSub.add(new AnalysedModuleDTO("domain.locationbased.foursquare", "foursquare", foursquareSub));
		locationbasedSub.add(new AnalysedModuleDTO("domain.locationbased.latitude", "latitude", latitudeSub));
		
		
		ArrayList<AnalysedModuleDTO> domainSub = new ArrayList<AnalysedModuleDTO>();
		domainSub.add(new AnalysedModuleDTO("domain.locationbased", "locationbased", locationbasedSub));
		
		
		ArrayList<AnalysedModuleDTO> foursquare1Sub = new ArrayList<AnalysedModuleDTO>();
		foursquare1Sub.add(new AnalysedModuleDTO("infrastructure.socialmedia.locationbased.foursquare.AccountDAO", "AccountDAO"));
		foursquare1Sub.add(new AnalysedModuleDTO("infrastructure.socialmedia.locationbased.foursquare.FriendsDAO", "FriendsDAO"));
		foursquare1Sub.add(new AnalysedModuleDTO("infrastructure.socialmedia.locationbased.foursquare.IMap", "IMap"));
		foursquare1Sub.add(new AnalysedModuleDTO("infrastructure.socialmedia.locationbased.foursquare.HistoryDAO", "HistoryDAO"));
		
		ArrayList<AnalysedModuleDTO> locationbased1Sub = new ArrayList<AnalysedModuleDTO>();
		locationbased1Sub.add(new AnalysedModuleDTO("infrastructure.socialmedia.locationbased.foursquare", "foursquare", foursquare1Sub));
		
		ArrayList<AnalysedModuleDTO> socialmediaSub = new ArrayList<AnalysedModuleDTO>();
		socialmediaSub.add(new AnalysedModuleDTO("infrastructure.socialmedia.locationbased", "locationbased", locationbased1Sub));
		
		ArrayList<AnalysedModuleDTO> infrastructureSub = new ArrayList<AnalysedModuleDTO>();
		infrastructureSub.add(new AnalysedModuleDTO("infrastructure.socialmedia", "socialmedia", socialmediaSub));
		
		ArrayList<AnalysedModuleDTO> analysedSub = new ArrayList<AnalysedModuleDTO>();
		analysedSub.add(new AnalysedModuleDTO("domain", "domain", domainSub));
		analysedSub.add(new AnalysedModuleDTO("infrastructure", "infrastructure", infrastructureSub));
		
		AnalysedModuleDTO analysedModules = new AnalysedModuleDTO("", "root", analysedSub);
		analysed = new HashMap<String, ArrayList<Object>>();

		List<AnalysedModuleDTO> rootElement = new ArrayList<AnalysedModuleDTO>();
		rootElement.add(analysedModules);
		GenerateHashmap(rootElement);
		
		
		addDependency(new DependencyDTO("domain.locationbased.foursquare.History", "infrastructure.socialmedia.locationbased.foursquare.HistoryDAO", "extends", 10));
		addDependency(new DependencyDTO("domain.locationbased.latitude.Account", "infrastructure.socialmedia.locationbased.latitude.AccountDAO", "object", 11));
		addDependency(new DependencyDTO("domain.locationbased.latitude.Friends", "infrastructure.socialmedia.locationbased.latitude.FriendsDAO", "extends", 10));
		addDependency(new DependencyDTO("domain.locationbased.foursquare.Map", "infrastructure.socialmedia.locationbased.foursquare.IMap", "extends", 10));
		addDependency(new DependencyDTO("domain.locationbased.foursquare.Account", "infrastructure.socialmedia.locationbased.foursquare.AccountDAO", "object", 10));
		addDependency(new DependencyDTO("domain.locationbased.foursquare.Friends", "infrastructure.socialmedia.locationbased.foursquare.FriendsDAO", "extends", 10));
		addDependency(new DependencyDTO("domain.locationbased.latitude.Map", "infrastructure.socialmedia.locationbased.latitude.IMap", "implements", 10));
	
	}
	
	private void GenerateHashmap(List<AnalysedModuleDTO> subModules) {
		if(subModules != null){
			for(AnalysedModuleDTO currentModule : subModules){
				ArrayList<Object> objecten = new ArrayList<Object>();
				ArrayList<DependencyDTO> dependencies = new ArrayList<DependencyDTO>();
				objecten.add(currentModule);
				objecten.add(dependencies);
				
				analysed.put(currentModule.uniqueName, objecten);
				GenerateHashmap(currentModule.subModules);
			}
		}
	}
	
	private void addDependency(DependencyDTO dependency){
		ArrayList<Object> getElement = analysed.get(dependency.from);
		
		ArrayList<DependencyDTO> dependencies = (ArrayList<DependencyDTO>) getElement.get(1);
		dependencies.add(dependency);
		
		
	}
	
	
	
	@Override
	public void analyseApplication() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DependencyDTO[] getDependency(String from, String to) {
		
		ArrayList<DependencyDTO> allDependencies = new ArrayList<DependencyDTO>();
			
		for(String s : analysed.keySet()){
			if(s.indexOf(from) == -1){
				continue;
			}
			
			ArrayList<Object> currentElement = analysed.get(s);
			for(DependencyDTO dependency: (ArrayList<DependencyDTO>) currentElement.get(1)){
				if(dependency.to.indexOf(to) != -1){
					allDependencies.add(dependency);
				}
			}
		}
		
		
		if(allDependencies.size() != 0){
			DependencyDTO[] dependencyDTO = new DependencyDTO[allDependencies.size()];
			
			int iterator = 0;
			for(DependencyDTO d : allDependencies){
				dependencyDTO[iterator] = d;
				iterator++;
			}
			
			return dependencyDTO;
			
			
		}
		
		return new DependencyDTO[0];
	}

	@Override
	public DependencyDTO[] getDependency(String from) {
	
		ArrayList<DependencyDTO> dependencies = (ArrayList<DependencyDTO>) analysed.get(from).get(1);
		if(dependencies.size() == 0){
			return new DependencyDTO[0];
		}
		
		DependencyDTO[] matchDependency = new DependencyDTO[dependencies.size()];
		int iterator = 0;
		for(DependencyDTO d : matchDependency){
			matchDependency[iterator] = d;
		}
		
		return matchDependency;
	}

	@Override
	public String[] getAvailableLanguages() {
		String[] languages = {"Java", "C#"};
		return languages;
	}

	@Override
	public AnalysedModuleDTO[] getModules() {
		AnalysedModuleDTO rootElement = (AnalysedModuleDTO) analysed.get("").get(0);
		
		AnalysedModuleDTO[] returnModules = new AnalysedModuleDTO[rootElement.subModules.size()];
		
		int iterator = 0;
		for(AnalysedModuleDTO module : rootElement.subModules){
			returnModules[iterator] = module;
			module.subModules = null;
			iterator++;
		}
		
		return returnModules;
	}

	@Override
	public AnalysedModuleDTO[] getChildModulesInModule(String from) {
		AnalysedModuleDTO getElement = (AnalysedModuleDTO) analysed.get(from).get(0);
		
		AnalysedModuleDTO[] modules = new AnalysedModuleDTO[getElement.subModules.size()];
		
		int iterator = 0;
		for(AnalysedModuleDTO module : getElement.subModules){
			modules[iterator] = module;
			iterator++;
		}
		
		return modules;
	}

	@Override
	public AnalysedModuleDTO[] getChildModulesInModule(String from, int depth) {
		int currentDepth = 0;
		
		
		if(depth == 1){
			return this.getChildModulesInModule(from);
		}
		
		AnalysedModuleDTO getElement = (AnalysedModuleDTO) analysed.get(from).get(0);
		
		AnalysedModuleDTO[] modules = new AnalysedModuleDTO[getElement.subModules.size()];
		
		int iterator = 0;
		for(AnalysedModuleDTO module : getElement.subModules){
			modules[iterator] = module;
			iterator++;
		}
		
		currentDepth = 1;
		AnalysedModuleDTO[] rightDepthModules = modules;
		
		while(currentDepth != depth){
			
			
			rightDepthModules = NextDepth(rightDepthModules);
			currentDepth++;
		}
		
		for(AnalysedModuleDTO m : rightDepthModules){
			m.subModules = null;
		}
		
		
		
		return modules;
	}

	@Override
	public AnalysedModuleDTO getParentModule(String child) {
		if(child.indexOf(".") == -1){
			return new AnalysedModuleDTO("", "");
		}
		
		String[] pathSplitted = child.split("\\.");
		String parentPath = pathSplitted[0];
		for(int iterator = 1; iterator < pathSplitted.length-1; iterator++){
			if(iterator != pathSplitted.length-1){		
				parentPath += "." + pathSplitted[iterator];
			}
		}
		
		return (AnalysedModuleDTO) analysed.get(parentPath).get(0);
	}


	private AnalysedModuleDTO[] NextDepth(AnalysedModuleDTO[] modules){
		List<AnalysedModuleDTO> test = new ArrayList<AnalysedModuleDTO>();
		for(AnalysedModuleDTO module : modules){		
			if(module.subModules != null){
				for (AnalysedModuleDTO submodule : module.subModules) {
					test.add(submodule);
				}
			}
		}
		
		AnalysedModuleDTO[] depthModules = new AnalysedModuleDTO[test.size()];
		int iterator = 0;
		for(AnalysedModuleDTO save : test){
			depthModules[iterator] = save;
			iterator++;
		}
		
		return depthModules;
	}
	
	
	
	

}
