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

/**
 * 
 * @author Ramin Esfandiari </br>
 * 
 *         7. aug. 2017 </br>
 *         </br>
 *         The CrudRestServiceApiController class is the OrderSofts' REST
 *         service, responsible for exposing an API to allow
 *         Create-Read-Update-Delete (CRUD) operations.</br>
 *         <strong>Examples:</strong></br>
 *         </br>
 *         Get all orders (GET): {@link http://localhost:8080/api/orders} </br>
 *         Get all orders by customer name (GET):
 *         {@link http://localhost:8080/api/orders/Ramin%20Esfandiari} </br>
 *         Update an order by customer name (PUT):
 *         {@link http://localhost:8080/api/orders/Ramin%20Esfandiari} </br>
 *         Delete an order by order id (DELETE):
 *         {@link http://localhost:8080/api/orders/1} </br>
 */
@RestController
public class CrudRestServiceApiController {

	@Autowired
	OrderRepository repo; // An order repository object to handle the CRUD-operations.

	/**
	 * Maps this method to the corresponding GET request from the client-side.
	 * 
	 * @return The list of all orders in JSON-format.
	 */
	@RequestMapping("/api/orders") // Value and RequestMethod.GET by default.
	public List<Order> getAllOrders() {
		return repo.findAll(); // Find and return all orders from database.
	}

	/**
	 * Maps this method to the corresponding GET request from client-side. Gets all
	 * registered orders for the queried customer.
	 * 
	 * @param customerName
	 *            Name of the customer to query. The customer name is passed as a
	 *            parameter in the URI.
	 * @return The list of orders for the queried customer.
	 */
	@RequestMapping(value = "/api/orders/{customerName}", method = RequestMethod.GET)
	public List<Order> getOrder(@PathVariable("customerName") String customerName) {
		return repo.findByCustomerName(customerName); // Find and return all registered orders for given customer.
	}

	@RequestMapping(value = "/api/orders/{id}", method = RequestMethod.GET)
	public Order getOrder(@PathVariable("id") long id) {
		return repo.findById(id);
	}

	/**
	 * Maps this method to the corresponding POST request from client-side. Creates
	 * a new order and persists it to the database.</br>
	 * When sending a request body, ignore the id field as it is generated
	 * automatically.
	 * 
	 * @param newOrder
	 *            The new order to create and send.
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/api/orders")
	public void addOrder(@RequestBody Order newOrder) {
		repo.save(newOrder); // Persist the given order to database.
	}

	/**
	 * Maps this method to the corresponding PUT request from client-side. Updates
	 * an existing order.
	 * 
	 * @param updateOrder
	 *            The order to update/edit.
	 * @param customerName
	 *            Name of the customer to update/edit. The customer name is passed
	 *            as a parameter in the URI.
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/api/orders/{customerName}")
	public void updateOrder(@RequestBody Order updateOrder, @PathVariable String customerName) {
		repo.save(updateOrder); // Update/Edit the existing user.
	}

	/**
	 * Maps this method to the corresponding DELETE request from client-side.
	 * Deletes a given order by its id.
	 * 
	 * @param orderId
	 *            Id of the order to delete. The id is passed as a parameter in the
	 *            URI.
	 */
	@RequestMapping(value = "/api/orders/{orderId}", method = RequestMethod.DELETE)
	public void deleteOrder(@PathVariable("orderId") long orderId) {
		repo.deleteOrderById(orderId); // Delete order by given order id.
	}
}
