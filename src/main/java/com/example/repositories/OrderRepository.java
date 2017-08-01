package com.example.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	public List<Order> findByCostumerName(String costumerName);
	
	public List<Order> findByPhoneNumber(int phonenumber);

	@Transactional
	public void deleteOrderById(long id);
	
	public Order findById(long id);
}
