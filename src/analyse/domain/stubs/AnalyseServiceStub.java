package analyse.domain.stubs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import analyse.domain.analyseservice.AnalyseService;
import analyse.domain.analyseservice.dto.AnalysedModuleDTO;
import analyse.domain.analyseservice.dto.DependencyDTO;

public class AnalyseServiceStub implements AnalyseService{

	private HashMap<String, AnalysedModuleDTO> analysed;
	
	
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
		analysed = new HashMap<String, AnalysedModuleDTO>();
		
		List<AnalysedModuleDTO> rootElement = new ArrayList<AnalysedModuleDTO>();
		rootElement.add(analysedModules);
		GenerateHashmap(rootElement);
	}
	
	private void GenerateHashmap(List<AnalysedModuleDTO> subModules) {
		if(subModules != null){
			for(AnalysedModuleDTO currentModule : subModules){
				analysed.put(currentModule.uniqueName, currentModule);
				GenerateHashmap(currentModule.subModules);
			}
		}
	}
	
	
	
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
		String[] languages = {"Java", "C#"};
		return languages;
	}

	@Override
	public AnalysedModuleDTO[] getModules() {
		AnalysedModuleDTO rootElement = analysed.get("");
		
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
		AnalysedModuleDTO getElement = analysed.get(from);
		
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
		
		AnalysedModuleDTO getElement = analysed.get(from);
		
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
			return null;
		}
		
		String[] pathSplitted = child.split("\\.");
		String parentPath = pathSplitted[0];
		for(int iterator = 1; iterator < pathSplitted.length-1; iterator++){
			if(iterator != pathSplitted.length-1){		
				parentPath += "." + pathSplitted[iterator];
			}
		}
		
		return analysed.get(parentPath);
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
