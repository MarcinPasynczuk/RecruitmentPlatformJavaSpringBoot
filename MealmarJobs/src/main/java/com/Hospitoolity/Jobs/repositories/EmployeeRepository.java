package com.Hospitoolity.Jobs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Hospitoolity.Jobs.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	boolean existsByNameOrEmailOrPhoneNumber(String name, String email, String phoneNumber);
}