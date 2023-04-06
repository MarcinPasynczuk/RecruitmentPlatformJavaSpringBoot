package com.Hospitoolity.Jobs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Hospitoolity.Jobs.repositories.EmployeeRepository;

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;

    public boolean employeeExists(String name, String email, String phoneNumber) {
        return employeeRepository.existsByNameOrEmailOrPhoneNumber(name, email, phoneNumber);
    }
}