package analyse.service;

import analyse.dto.ClassDTO;
import analyse.dto.DependencyDTO;
import analyse.dto.PackageDTO;

public class SetupDTOs {

	public PackageDTO package1;
	public PackageDTO package2;
	public PackageDTO package1_1;
	
	public ClassDTO class1A;
	public ClassDTO class1B;
	
	public ClassDTO class1_1A;
	public ClassDTO class1_1B;
	
	public ClassDTO class2A;
	public ClassDTO class2B;
	
	public DependencyDTO dependencyDTO1;
	public DependencyDTO dependencyDTO2;
	
	public SetupDTOs(){
		
		package1 = createPackageDTO("package1","husacct.test.package1");
		package2 = createPackageDTO("package2","husacct.test.package2");
		package1_1 = createPackageDTO("package1_1","husacct.test.package1.package1_1");
		
		class1A = createClassDTO("class1A","class","husacct.test.package1.class1A");
		class1B = createClassDTO("class1B","class","husacct.test.package1.class1B");
		
		class1_1A = createClassDTO("class1_1A","class","husacct.test.package1.package1_1.class1_1A");
		class1_1B = createClassDTO("class1_1B","class","husacct.test.package1.package1_1.class1_1B");
		
		class2A = createClassDTO("class2A","interface","husacct.test.package2.class2A");
		class2B = createClassDTO("class2B","class","husacct.test.package2.class2B");
		
		package1.addClassDTO(class1A);
		package1.addClassDTO(class1B);
		package1.addPackageDTO(package1_1);
		package1_1.addClassDTO(class1_1A);
		package1_1.addClassDTO(class1_1B);
		
		package2.addClassDTO(class2A);
		package2.addClassDTO(class2B);
		
		dependencyDTO1 = createDependencyDTO("husacct.test.package1.class1B", "husacct.test.package1.class1A", "Extend", 3);
		dependencyDTO2 = createDependencyDTO("husacct.test.package1.package1_1.class1_1B", "husacct.test.package1.class1A", "Association", 15);
	}
	
	public ClassDTO createClassDTO(String name, String type, String uniqueName){
		ClassDTO classDTO = new ClassDTO();
		classDTO.setName(name);
		classDTO.setType(type);
		classDTO.setUniqueName(uniqueName);
		
		return classDTO;
	}
	
	public PackageDTO createPackageDTO(String name, String uniqueName){
		PackageDTO packageDTO = new PackageDTO();
		packageDTO.setName(name);
		packageDTO.setUniqueName(uniqueName);
		
		return packageDTO;
	}
	
	public DependencyDTO createDependencyDTO(String from, String to, String type, int line){
		DependencyDTO dependencyDTO = new DependencyDTO();
		dependencyDTO.setFrom(from);
		dependencyDTO.setTo(to);
		dependencyDTO.setType(type);
		dependencyDTO.setLineNumber(line);
		
		return dependencyDTO;
	}
}
