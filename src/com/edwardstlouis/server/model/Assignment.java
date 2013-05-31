package com.edwardstlouis.server.model;

public class Assignment {
	
	private String id;
	private String name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	// This is required for Request Factory...
	public int getVersion() {
		return 0;
	}
	
	public static Assignment findAssignment(String id) {
		Assignment s = new Assignment();
		s.setId(id);
		s.setName("Assignment With Id-" + id);
		return s;
	}
	
}
