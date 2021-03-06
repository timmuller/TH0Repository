package husacct.validate.task.export;

import husacct.validate.domain.ConfigurationServiceImpl;
import husacct.validate.domain.configuration.ActiveRuleType;
import husacct.validate.domain.validation.Severity;
import husacct.validate.domain.validation.Violation;
import husacct.validate.domain.validation.ViolationHistory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Element;

public class ExportController {

	private final ExportFactory exportFactory;

	public ExportController() {
		this.exportFactory = new ExportFactory();
	}

	public Element exportAllData(ConfigurationServiceImpl configuration){
		Element rootValidateElement = new Element("validate");
		rootValidateElement.addContent(exportViolationsXML(configuration.getAllViolations().getValue()));
		rootValidateElement.addContent(exportSeveritiesXML(configuration.getAllSeverities()));
		rootValidateElement.addContent(exportSeveritiesPerTypesPerProgrammingLanguagesXML(configuration.getAllSeveritiesPerTypesPerProgrammingLanguages()));
		rootValidateElement.addContent(exportViolationHistory(configuration.getViolationHistory()));
		rootValidateElement.addContent(exportActiveViolationTypesPerRuleTypes(configuration.getActiveViolationTypes()));
		return rootValidateElement;
	}

	private Element exportViolationsXML(List<Violation> violations) {
		return exportFactory.exportViolations(violations);
	}

	private Element exportSeveritiesXML(List<Severity> severities) {
		return exportFactory.exportSeverities(severities);
	}

	private Element exportSeveritiesPerTypesPerProgrammingLanguagesXML(HashMap<String, HashMap<String, Severity>> allSeveritiesPerTypesPerProgrammingLanguages) {
		return exportFactory.exportSeveritiesPerTypesPerProgrammingLanguages(allSeveritiesPerTypesPerProgrammingLanguages);
	}
	
	private Element exportViolationHistory(List<ViolationHistory> violationHistories) {
		return exportFactory.exportViolationHistory(violationHistories);
	}
	private Element exportActiveViolationTypesPerRuleTypes(Map<String, List<ActiveRuleType>> activeViolationTypes) {
		return exportFactory.exportActiveViolationTypes(activeViolationTypes);
	}
}