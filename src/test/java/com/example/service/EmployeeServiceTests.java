package com.example.service;

import com.example.exception.ResourceAlreadyExistsException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Employee;
import com.example.repository.EmployeeRepository;
import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class) // To notify tests that we are using Mockito annotations
public class EmployeeServiceTests {
    @Mock
    private EmployeeRepository employeeRepositoryMock;

    @InjectMocks
    private EmployeeService underTest;

    private Employee employee;

    @BeforeEach
    public void setup() {
        employee = Employee.builder()
                .id(1L)
                .firstName("Adarsh")
                .lastName("Kumar")
                .email("adarsh@gmail.com")
                .build();
    }

    // save operation
    @Test
    @DisplayName("saveEmployee")
    public void givenEmployeeObject_whenSaveEmployee_thenSavedEmployee() {
        // given
        given(employeeRepositoryMock.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepositoryMock.save(employee)).willReturn(employee);

        // when
        Employee savedEmployee = underTest.saveEmployee(employee);

        // then
        assertThat(savedEmployee).isNotNull();
    }

    // save operation - throws ResourceAlreadyExistsException
    @Test
    @DisplayName("saveEmployee - throws ResourceAlreadyExistsException")
    public void givenEmployeeObject_whenSaveEmployee_thenThrowsResourceAlreadyExistsException() {
        // given
        given(employeeRepositoryMock.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        // when
        assertThatExceptionOfType(ResourceAlreadyExistsException.class)
                .isThrownBy(() -> underTest.saveEmployee(employee))
                .withMessageContaining("Employee already exist with given email:%s", employee.getEmail());

        // then
        verify(employeeRepositoryMock, never()).save(any(Employee.class));
    }

    // get all operation
    @Test
    @DisplayName("getAllEmployee")
    public void givenEmployeeList_whenGetAllEmployee_thenEmployeesList() {
        // given
        Employee employee1 = Employee.builder()
                .id(1L)
                .firstName("Anand")
                .lastName("Kumar")
                .email("anand@gmail.com")
                .build();

        given(employeeRepositoryMock.findAll()).willReturn(List.of(employee, employee1));

        // when
        List<Employee> employeeList = underTest.getAllEmployees();

        // then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    // get all operation - empty list
    @Test
    @DisplayName("getAllEmployee - empty list")
    public void givenEmptyEmployeeList_whenGetAllEmployee_thenEmptyEmployeesList() {
        // given
        given(employeeRepositoryMock.findAll()).willReturn(Collections.emptyList());

        // when
        List<Employee> employeeList = underTest.getAllEmployees();

        // then
        assertThat(employeeList).isEmpty();
    }

    // get by id operation
    @Test
    @DisplayName("getEmployeeById")
    public void givenEmployeeId_whenGetEmployeeById_thenEmployee() {
        // given
        given(employeeRepositoryMock.findById(employee.getId())).willReturn(Optional.of(employee));

        // when
        Employee savedEmployee = underTest.getEmployeeById(employee.getId()).orElse(null);

        // then
        assertThat(savedEmployee).isNotNull();
    }

    // update operation
    @Test
    @DisplayName("updateEmployee")
    public void givenEmployeeObject_whenUpdateEmployee_thenUpdatedEmployee() {
        // given
        Employee updateData = Employee.builder()
                .id(1)
                .firstName("Adarsh")
                .lastName("Anand")
                .email("adarsh.anand@gmail.com")
                .build();

        given(employeeRepositoryMock.findById(employee.getId())).willReturn(Optional.of(employee));
        given(employeeRepositoryMock.save(updateData)).willReturn(updateData);

        // when
        Employee updatedEmployee = underTest.updateEmployee(updateData);

        // then
        assertThat(updatedEmployee.getFirstName()).isEqualTo(updateData.getFirstName());
        assertThat(updatedEmployee.getLastName()).isEqualTo(updateData.getLastName());
        assertThat(updatedEmployee.getEmail()).isEqualTo(updateData.getEmail());
    }

    // update operation  - throws ResourceNotFoundException
    @Test
    @DisplayName("updateEmployee - throws ResourceNotFoundException")
    public void givenEmployeeObject_whenUpdateEmployee_thenThrowsResourceNotFoundException() {
        // given
        given(employeeRepositoryMock.findById(employee.getId())).willReturn(Optional.empty());

        Employee updateData = Employee.builder()
                .id(1)
                .firstName("Adarsh")
                .lastName("Anand")
                .email("adarsh.anand@gmail.com")
                .build();

        // when
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> underTest.updateEmployee(updateData))
                .withMessageContaining("Employee doesn't exist with given email:%s", updateData.getId());

        // then
        verify(employeeRepositoryMock, never()).save(any(Employee.class));
    }

    // delete operation
    @Test
    @DisplayName("deleteEmployee")
    public void givenEmployeeId_whenDeleteEmployee_thenCheckEmployeeDeletion() {
        // given
        long employeeId = employee.getId();
        willDoNothing().given(employeeRepositoryMock).deleteById(employeeId);

        // when
        underTest.deleteEmployee(employeeId);

        // then
        verify(employeeRepositoryMock, times(1)).deleteById(employeeId);
    }
}
