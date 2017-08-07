package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.models.Order;
import com.example.repositories.OrderRepository;

@RestController
public class RestAPIController {

	@Autowired
	OrderRepository repo;

	@RequestMapping("/api/orders")
	public List<Order> getAllOrders() {

		return repo.findAll();
	}

	@RequestMapping(value = "/api/orders/{customerName}", method = RequestMethod.GET)
	public List<Order> getOrder(@PathVariable("customerName") String customerName) {
		return repo.findByCostumerName(customerName);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/api/orders")
	public void addOrder(@RequestBody Order newOrder) {
		repo.save(newOrder);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/api/orders/{customerName}")
	public void updateOrder(@RequestBody Order updateOrder, @PathVariable String customerName) {
		repo.save(updateOrder);
	}

	@RequestMapping(value = "/api/orders/{customerName}", method = RequestMethod.DELETE)
	public void deleteOrder(@PathVariable("customerName") long customerName) {
		repo.deleteOrderById(customerName);
	}
}
