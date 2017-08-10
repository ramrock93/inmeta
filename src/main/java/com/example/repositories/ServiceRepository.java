package com.example.repositories;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.Service;

@Scope("prototype")
public interface ServiceRepository extends JpaRepository<Service, Long> {

}
