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

@Entity
@Table(name = "order_table")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String costumerName;
	private int phoneNumber;
	private String email;
	private String fromAddress;
	private String toAddress;
	private String description;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate date;

	private Service service;

	public Order() {
	}

	public Order(String costumerName, int phoneNumber, String email, String fromAddress, String toAddress,
			Service service, String description, LocalDate date) {

		super();
		this.costumerName = costumerName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.fromAddress = fromAddress;
		this.toAddress = toAddress;
		this.service = service;
		this.description = description;

		this.date = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCostumerName() {
		return this.costumerName;
	}

	public void setCostumerName(String costumerName) {
		this.costumerName = costumerName;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Order clone() throws CloneNotSupportedException {
		return (Order) super.clone();
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", CostumerName=" + costumerName + ", phonenumber=" + phoneNumber + ", email="
				+ email + ", fromAddress=" + fromAddress + ", toAddress=" + toAddress + ", description=" + description
				+ ", date=" + date + ", service=" + service + "]";
	}
}
