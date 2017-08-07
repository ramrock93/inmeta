package com.example.exceptions;

/**
 * The FormNotValidatedException class extens the Exception class and represents
 * an exception for when a form is not validated.
 * 
 * @author Ramin Esfandiari </br>
 *         7. aug. 2017 </br>
 *
 */
public class FormNotValidatedexception extends Exception {

	/**
	 * Constructs a new instance with a given error message.
	 * 
	 * @param message
	 */
	public FormNotValidatedexception(String message) {
		super(message); // Construct the superclass and pass the message as argument.
	}
}
