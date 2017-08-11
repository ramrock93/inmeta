package com.example.repositories;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.Customer;

@Scope("prototype")
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	public Customer findCustomerByName(String name);

	public List<Customer> findCustomersByName(String name);

	public Customer findCustomerByPhone(int phone);
}
