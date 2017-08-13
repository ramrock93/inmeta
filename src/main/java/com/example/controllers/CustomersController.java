package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.models.Customer;
import com.example.repositories.CustomerRepository;

@RestController
public class CustomersController {

	@Autowired
	private CustomerRepository customerRepo;

	@RequestMapping(value = "/api/customers", method = RequestMethod.GET)
	public List<Customer> getCustomers() {
		return customerRepo.findAll();
	}

	@RequestMapping(value = "/api/customers/{name}", method = RequestMethod.GET)
	public List<Customer> findCustomersByName(@PathVariable("name") String name) {
		return customerRepo.findCustomersByName(name);
	}

	@RequestMapping(value = "/api/customer/{phone}", method = RequestMethod.GET)
	public Customer findCustomerByPhone(@PathVariable("phone") int phone) {
		return customerRepo.findCustomerByPhone(phone);
	}

}
