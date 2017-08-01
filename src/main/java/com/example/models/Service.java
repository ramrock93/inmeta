package com.example.models;

import com.vaadin.spring.annotation.SpringComponent;

public enum Service {
	
	MOVING("Moving"), PACKING("Packing"), CLEANING("Cleaning");
	
	private String description;
	
	Service(String description)
	{
		this.description =  description;
	}
	
	public String getDescription()
	{
		return this.description;
	}

}
