package com.example.models;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * The Order class represents an order entity which holds information i.e.
 * customer name,</br>
 * phone number, email, what kind of service to be ordered, when it is to be
 * carried out, and etc.
 * 
 * @author Ramin Esfandiari</br>
 *         7. aug. 2017
 *
 */
@Entity
@Table(name = "order_table")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id; // The primary key of the table "order_table". This is generated automatically.

	private String customerName; // Holds the customer name for this order (should be refactored to a own
									// customer
									// class in later implementations).

	private int phoneNumber; // Holds the phone number of the customer for this order.
	private String email; // Holds the email address of the customer for this order.
	private String fromAddress; // Holds the address the customer is moving from.
	private String toAddress; // Holds the address the customer is moving to.
	private String description; // Holds a description of this order, if any.

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // Formats the value as "yyyy-MM-dd" when the
																			// date field is parsed as JSON.
	private LocalDate date; // Holds the date of which this order is to be carried out.

	private Service service; // Holds what kind of service is to be carried out (should be refactored to
								// allow more than one service pr. order).

	/**
	 * A default constructor to be used by JPA. This should not be used otherwise.
	 * Use the parameterized constructor.
	 */
	public Order() {
	}

	/**
	 * Constructs a new Order object.
	 * 
	 * @param customerName
	 *            The full name of the customer to pass.
	 * @param phoneNumber
	 *            The phone number of the customer to pass.
	 * @param email
	 *            The email address of the customer to pass.
	 * @param fromAddress
	 *            The address the customer is moving from to pass.
	 * @param toAddress
	 *            The address of the customer is moving to, to pass.
	 * @param service
	 *            The type of service to pass.
	 * @param description
	 *            The description for this order to pass.
	 * @param date
	 *            The date in which this order is to be carried out.
	 */
	public Order(String customerName, int phoneNumber, String email, String fromAddress, String toAddress,
			Service service, String description, LocalDate date) {

		super();
		this.customerName = customerName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.fromAddress = fromAddress;
		this.toAddress = toAddress;
		this.service = service;
		this.description = description;
		this.date = date;
	}

	/**
	 * Gets the id for this order.
	 * 
	 * @return The id to return.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets a new id for this order. Should not be used, as it is managed by JPA.
	 * 
	 * @param id
	 *            The new id to pass.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the customer name.
	 * 
	 * @return Name of the customer to return.
	 */
	public String getCustomerName() {
		return this.customerName;
	}

	/**
	 * Sets a new customer name.
	 * 
	 * @param customerName
	 *            The new name to set.
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * Gets the phone number
	 * 
	 * @return
	 */
	public int getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Sets a new phone number.
	 * 
	 * @param phoneNumber
	 *            The phone number to set.
	 */
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Gets the email address.
	 * 
	 * @return The email address to return.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email address.
	 * 
	 * @param email
	 *            The email address to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the address the customer is moving from.
	 * 
	 * @return The from address to return.
	 */
	public String getFromAddress() {
		return fromAddress;
	}

	/**
	 * Sets the address the customer is moving from.
	 * 
	 * @param fromAddress
	 *            The address the customer is moving from to set.
	 */
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	/**
	 * Gets the address the customer is moving to.
	 * 
	 * @return The to address to return.
	 */
	public String getToAddress() {
		return toAddress;
	}

	/**
	 * Sets the address the customer is moving to.
	 * 
	 * @param toAddress
	 *            The address the customer is moving to, to set.
	 */
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	/**
	 * Gets the service type for this order.
	 * 
	 * @return The type of service to return.
	 */
	public Service getService() {
		return service;
	}

	/**
	 * Sets the type of service for this order.
	 * 
	 * @param service
	 *            The type of service to set.
	 */
	public void setService(Service service) {
		this.service = service;
	}

	/**
	 * Gets the date for which this order is to be carried out.
	 * 
	 * @return The for which this order to return.
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Sets the date for which this order is to be carried out.
	 * 
	 * @param date
	 *            The date to set.
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * Gets the description for this order.
	 * 
	 * @return The description for this order to return.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description for this order.
	 * 
	 * @param description
	 *            The description for this order to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets a string representation of this order, and its values.
	 */
	@Override
	public String toString() {
		return "Order [id=" + id + ", CostumerName=" + customerName + ", phonenumber=" + phoneNumber + ", email="
				+ email + ", fromAddress=" + fromAddress + ", toAddress=" + toAddress + ", description=" + description
				+ ", date=" + date + ", service=" + service + "]";
	}
}
