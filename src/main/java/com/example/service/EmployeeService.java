package com.example.service;

import com.example.exception.ResourceAlreadyExistsException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Employee;
import com.example.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (savedEmployee.isPresent()) {
            throw new ResourceAlreadyExistsException("Employee already exist with given email:" + employee.getEmail());
        }
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(long id) {
        return employeeRepository.findById(id);
    }

    public Employee updateEmployee(Employee updatedEmployee) {
        Optional<Employee> employee = employeeRepository.findById(updatedEmployee.getId());
        if(employee.isPresent()) {
            employee.get().setFirstName(updatedEmployee.getFirstName());
            employee.get().setLastName(updatedEmployee.getLastName());
            employee.get().setEmail(updatedEmployee.getEmail());

            return employeeRepository.save(updatedEmployee);
        } else {
            throw new ResourceNotFoundException("Employee doesn't exist with given email:" + updatedEmployee.getId());
        }
    }

    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }
}
