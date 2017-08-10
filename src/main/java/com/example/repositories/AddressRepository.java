package com.example.repositories;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.Address;

@Scope("prototype")
public interface AddressRepository extends JpaRepository<Address, Long> {

}
