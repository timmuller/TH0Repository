package analyse.test;
import static org.junit.Assert.*;

import java.util.List;

import analyse.dto.DependencyDTO;
import analyse.dto.PackageDTO;
import analyse.service.AnalyseServiceImpl;

import org.junit.Test;

public class AnalyseServiceTest {

	AnalyseServiceImpl analyseServiceImpl = new AnalyseServiceImpl();
	List<PackageDTO> packages = analyseServiceImpl.getPackages("");
	List<DependencyDTO> dependencieDTOs = analyseServiceImpl.getDependency("");
	
	@Test
	public void testAvailableLanguages(){
		List<String> languages = analyseServiceImpl.getAvailableLanguages();
		
		assertEquals("Java", languages.get(0));
		assertEquals("C#", languages.get(1));
	}
	
	@Test
	public void testIfPackagesAreSet(){
		assertEquals("package1", packages.get(0).getName());
		assertEquals("package1_1", packages.get(0).getPackagesDTO().get(0).getName());
		assertEquals("package2", packages.get(1).getName());
	}
	
	@Test
	public void testIfClassesAreSet(){
		assertEquals("class1A", packages.get(0).getClassesDTO().get(0).getName());
		assertEquals("class1B", packages.get(0).getClassesDTO().get(1).getName());
		
		assertEquals("class1_1A", packages.get(0).getPackagesDTO().get(0).getClassesDTO().get(0).getName());
		assertEquals("class1_1B", packages.get(0).getPackagesDTO().get(0).getClassesDTO().get(1).getName());
		
		assertEquals("class2A", packages.get(1).getClassesDTO().get(0).getName());
		assertEquals("class2B", packages.get(1).getClassesDTO().get(1).getName());
	}
	
	@Test
	public void testDependencyDTO(){
		assertEquals("husacct.test.package1.class1B", dependencieDTOs.get(0).getFrom());
		assertEquals("husacct.test.package1.class1A", dependencieDTOs.get(0).getTo());
		assertEquals("Extend", dependencieDTOs.get(0).getType());
		assertEquals(3, dependencieDTOs.get(0).getLineNumber());
		
		assertEquals("husacct.test.package1.package1_1.class1_1B", dependencieDTOs.get(1).getFrom());
		assertEquals("husacct.test.package1.class1A", dependencieDTOs.get(1).getTo());
		assertEquals("Association", dependencieDTOs.get(1).getType());
		assertEquals(15, dependencieDTOs.get(1).getLineNumber());
	}
}
