package com.edwardstlouis.server.model;

public class SampleModel {
	
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
	
	public static SampleModel findSampleModel(String id) {
		SampleModel s = new SampleModel();
		s.setId(id);
		s.setName("Name With Id-" + id);
		return s;
	}
	
}
