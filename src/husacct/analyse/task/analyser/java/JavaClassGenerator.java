package husacct.analyse.task.analyser.java;

import husacct.analyse.infrastructure.antlr.java.JavaParser;
import org.antlr.runtime.tree.CommonTree;

class JavaClassGenerator extends JavaGenerator{
	
	private static final int abstractNode = JavaParser.ABSTRACT;
	
	private String name = "";
	private String uniqueName = "";
	private String belongsToPackage = "";
	private String belongsToClass = null;
	
	private boolean isInnerClass = false; 
	private boolean isAbstract = false; 
	
	public JavaClassGenerator(String uniquePackageName){
		this.belongsToPackage = uniquePackageName;
	}
	
	public String generateModel(CommonTree commonTree) {
		
		this.name = commonTree.getChild(1).toString();
		if(belongsToPackage.equals("")) {
			this.uniqueName = commonTree.getChild(1).toString();
		}else{
			this.uniqueName = belongsToPackage + "." + commonTree.getChild(1).toString();
		}
		
		CommonTree modifierListTree = (CommonTree)commonTree.getFirstChildWithType(JavaParser.MODIFIER_LIST);
		
		if(modifierListTree.getFirstChildWithType(JavaParser.AT) != null){
			JavaAnnotationGenerator annotationGenerator = new JavaAnnotationGenerator(uniqueName);
			annotationGenerator.generateMethod((CommonTree) modifierListTree.getFirstChildWithType(JavaParser.AT));
		}
		
		this.isAbstract = isAbstract(modifierListTree);
		modelService.createClass(uniqueName, name, belongsToPackage, isAbstract, isInnerClass);
		return uniqueName;
	}
	
	public String generateModel(CommonTree commonTree, String parentClassName) {
		this.name = commonTree.getChild(1).toString();
		this.uniqueName = belongsToPackage + "." + commonTree.getChild(1).toString();
		this.isInnerClass = true;
		this.isAbstract = isAbstract((CommonTree)commonTree);
		this.isInnerClass = true;
		this.belongsToClass = parentClassName;
		modelService.createClass(uniqueName, name, belongsToPackage, isAbstract, isInnerClass, belongsToClass);
		return uniqueName;
	}
	
	private boolean isAbstract(CommonTree tree){
		return tree.getFirstChildWithType(abstractNode) != null;
	}
}
