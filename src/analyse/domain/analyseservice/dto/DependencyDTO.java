package com.hu.husacct.analyse.domain.analyseservice.dto;

public class DependencyDTO {
	
	public String from;
	public String to;
	public String type;
	public int lineNumber;
	
	DependencyDTO(String from, String to, String type, int lineNumber){
		this.from = from;
		this.to = to;
		this.type = type;
		this.lineNumber = lineNumber;
	}
}
