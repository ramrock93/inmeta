package com.example.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * The Order class represents an order entity which holds information i.e.
 * customer name,</br>
 * phone number, email, what kind of services to be ordered, when it is to be
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
	@Column(name = "order_id")
	private long id; // The primary key of the table "order_table". This is generated automatically.

	@ManyToOne
	@JsonBackReference
	private Customer customer;

	@OneToOne
	private Address movingTo; // Holds the address the customer is moving to.

	@Column(name = "description")
	private String description; // Holds a description of this order, if any.

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // Formats the value as "yyyy-MM-dd" when the
	@Column(name = "date") // date field is parsed as JSON.
	private LocalDate date; // Holds the date of which this order is to be carried out.

	@OneToMany
	@Column(name = "services")
	private List<Service> services; // Holds what kind of services is to be carried out (should be refactored to
	// allow more than one services pr. order).

	/**
	 * A default constructor to be used by JPA. This should not be used otherwise.
	 * Use the parameterized constructor.
	 */
	public Order() {
	}

	public Order(Customer customer, Address movingTo, String description, LocalDate date, List<Service> services) {
		super();
		this.customer = customer;
		this.movingTo = movingTo;
		this.description = description;
		this.date = date;
		this.services = services;

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Address getMovingTo() {
		return movingTo;
	}

	public void setMovingTo(Address movingTo) {
		this.movingTo = movingTo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

}
