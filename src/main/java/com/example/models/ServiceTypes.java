package com.example.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The ServiceTypes enum represents all the possible services that the OrderSoft
 * provides.
 * 
 * @author Ramin Esfandiari </br>
 *         7. aug. 2017 </br>
 *
 */

public enum ServiceTypes {

	MOVING, PACKING, CLEANING;

}
