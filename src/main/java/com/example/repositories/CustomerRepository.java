package com.example.repositories;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.Customer;

@Scope("prototype")
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
