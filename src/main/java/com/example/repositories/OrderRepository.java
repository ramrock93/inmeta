package com.example.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.Order;
import com.vaadin.spring.annotation.SpringComponent;

/**
 * The OrderRepository interface extends the {@link JpaRepository} interface
 * which uses generics and reflection</br>
 * to generate concrete implementation of this interface. </br>
 * </br>
 * 
 * In other words, there is no need to implement this interface, as Spring will
 * provide that at runtime.</br>
 * </br>
 * 
 * As an interface cannot be instantiated, it is possible to autowire the
 * interface into any</br>
 * spring-managed bean, e.g. a class that is annotated with
 * {@link SpringComponent} or any other</br>
 * of the stereotypes.
 * 
 * To use this interface, use either field, constructor or method injection
 * using the </br>
 * {@link Autowired} annotation. See {@link https://goo.gl/chGTms} for more info
 * on autowiring.</br>
 * </br>
 * 
 * There is also possible to write own method signatures to create custom
 * queries. See {@link "https://goo.gl/XRp3Pm"} for more info.<br>
 * <br>
 * </br>
 * 
 * The Scope annotation {@code @Scope("prototype")} tells the spring container
 * that there shall be created a new Bean-instance of this class everytime<br>
 * it is requested. This annotation helps solve the issues with multiple
 * sessions. If this annotation is removed or replaced with
 * {@code @Singleton}<br>
 * a {@link java.lang.IllegalStateException} would be thrown if multiple session
 * would have opened. <br>
 * <br>
 * 
 * See also {@link "https://goo.gl/8gm2rx"} for more info on the Singleton
 * scope.<br>
 * See also {@link "https://goo.gl/YkzG7j"} for more info on the Prototype
 * scope.
 * 
 * @author Ramin Esfandiari </br>
 *         7. aug. 2017 </br>
 *
 */
@Scope("prototype")
public interface OrderRepository extends JpaRepository<Order, Long> {

	/**
	 * Finds and returns a list of orders matching the specified customer name.
	 * 
	 * @param customerName
	 *            The name of the customer to query.
	 * @return The list of orders matching the queried customer name.
	 */
	public List<Order> findByCustomerName(String customerName);

	/**
	 * Finds and returns a list of orders matching the specified phone number.
	 * 
	 * @param phoneNumber
	 *            The phone number to query.
	 * @return The list of orders matching the queried phone number to return.
	 */
	public List<Order> findByPhoneNumber(int phoneNumber);

	/**
	 * Deletes an order given the id of the order.
	 * 
	 * @param id
	 *            The id of the order to delete.
	 */
	@Transactional
	public void deleteOrderById(long id);

	/**
	 * Finds and returns an order given the id.
	 * 
	 * @param id
	 *            The id of the order to query.
	 * @return The order which matches the order id is returned.
	 */
	public Order findById(long id);
}
