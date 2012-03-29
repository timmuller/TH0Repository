package analyse.dto;

import java.util.ArrayList;
import java.util.List;

public class PackageDTO {

	private String name;
	private String uniqueName;
	private List<PackageDTO> packages = new ArrayList<PackageDTO>();
	private List<ClassDTO> classes = new ArrayList<ClassDTO>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUniqueName() {
		return uniqueName;
	}
	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}
 	
	public void addPackageDTO(PackageDTO pdto){
		packages.add(pdto);
	}
	
	public List<PackageDTO> getPackagesDTO(){
		return packages;
	}
	
	public void addClassDTO(ClassDTO cdto){
		classes.add(cdto);
	}
	
	public List<ClassDTO> getClassesDTO(){
		return classes;
	}
	
}
