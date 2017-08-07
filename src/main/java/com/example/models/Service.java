package com.example.models;

/**
 * The Service enum represents all the possible services that the OrderSoft
 * provides.
 * 
 * @author Ramin Esfandiari </br>
 *         7. aug. 2017 </br>
 *
 */
public enum Service {

	MOVING("Moving"), PACKING("Packing"), CLEANING("Cleaning");

	private String name; // The name of the service.

	/**
	 * Constructs a new Service object.
	 * 
	 * @param name
	 *            The name of the service to create.
	 */
	Service(String name) {
		this.name = name;
	}

	/**
	 * Gets the name of this service.
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

}
