package com.springmockitomvc.dao;

import com.springmockitomvc.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public void saveEmployee(Employee employee){
        System.out.println("Starting to save employee: " + employee);
        employeeRepository.save(employee);
        System.out.println("Completing save employee: "+ employee);
    }

    public List<Employee> getAllEmployees() {
        System.out.println("Starting to get all employees");
        List<Employee> employees =  employeeRepository.findAll();
        System.out.println("Getting all employees completely: " );
        return employees;

    }

    public Employee getEmployeeByName(String name){

        System.out.println("Starting to get employee by id " + name);
        Employee employee = employeeRepository.findByName(name).orElse(new Employee());
        System.out.println("Completing get employee by id " + name);
        return employee;
    }
}
