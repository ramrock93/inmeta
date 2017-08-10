package com.example.models;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "service_table")
public class Service {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Enumerated
	private ServiceTypes serviceType;
	
	@ManyToOne
	private Order order;

	public Service(ServiceTypes type) {
		super();
		this.serviceType = type;
	}

	private Service() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ServiceTypes getService() {
		return serviceType;
	}

	public Service setService(ServiceTypes service) {
		this.serviceType = service;
		return this;
	}
}
