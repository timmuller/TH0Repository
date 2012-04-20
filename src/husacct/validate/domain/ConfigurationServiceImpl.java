package husacct.validate.domain;

import husacct.validate.domain.configuration.SeverityConfigRepository;
import husacct.validate.domain.configuration.ViolationRepository;
import husacct.validate.domain.validation.Severity;
import husacct.validate.domain.validation.Violation;

import java.util.HashMap;
import java.util.List;

public class ConfigurationServiceImpl {
	private final SeverityConfigRepository severityConfig;
	private final ViolationRepository violationRepository;
	private HashMap<String, Severity> severitiesPerRuleTypes;

	public ConfigurationServiceImpl(){
		this.severityConfig = new SeverityConfigRepository();
		this.violationRepository = new ViolationRepository();
		this.severitiesPerRuleTypes = new HashMap<String, Severity>();
	}
	
	public void clearViolations(){
		violationRepository.clear();
	}

	public List<Violation> getAllViolations(){
		return violationRepository.getAllViolations();
	}

	public void addViolations(List<Violation> violations){
		violationRepository.addViolation(violations);
	}

	public List<Severity> getAllSeverities(){
		return severityConfig.getAllSeverities();
	}

	public void addSeverities(List<Severity> severities) {
		severityConfig.addSeverities(severities);
	}

	public HashMap<String, Severity> getSeveritiesPerRuleTypes() {
		return severitiesPerRuleTypes;
	}
	
	public void setSeveritiesPerRuleTypes(HashMap<String, Severity> severitiesPerRuleTypes){
		this.severitiesPerRuleTypes = severitiesPerRuleTypes;
	}
}
