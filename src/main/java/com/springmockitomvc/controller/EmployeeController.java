package com.springmockitomvc.controller;

import com.springmockitomvc.dao.EmployeeRepository;
import com.springmockitomvc.dao.EmployeeService;
import com.springmockitomvc.model.Employee;
import com.springmockitomvc.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/addEmployee")
    public Response addEmployee(@RequestBody Employee employee){

        employeeService.saveEmployee( employee);
        return new Response( employee.getId() +" inserted" , Boolean.TRUE);
    }

    @GetMapping("/employee/{name}")
    public Employee getEmployeeById(@PathVariable String name){
        return employeeService.getEmployeeByName(name);
    }
    @GetMapping("/employees")
    public Response getAllEmployees(){

        List<Employee> employees =  employeeService.getAllEmployees();
        return new Response("Record counts: " + employees.size() , Boolean.TRUE);
    }
}
