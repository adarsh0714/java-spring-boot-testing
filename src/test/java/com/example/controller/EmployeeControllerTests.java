package com.example.controller;

import com.example.model.Employee;
import com.example.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeServiceMock;

    // save operation
    @Test
    @DisplayName("createEmployee")
    public void givenEmployeeObject_whenCreateEmployee_thenSavedEmployee() throws Exception {
        // given
        Employee employee = Employee.builder()
                .firstName("Adarsh")
                .lastName("Kumar")
                .email("adarsh@gmail.com")
                .build();

        // whatever argument is passed to function is returned
        given(employeeServiceMock.saveEmployee(any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then
        response.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    // get all operation
    @Test
    @DisplayName("getAllEmployees")
    public void givenEmployeeList_whenGetAllEmployee_thenEmployeesList() throws Exception {
        // given
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(Employee.builder().firstName("Adarsh").lastName("Kumar").email("adarsh@gmail.com").build());
        listOfEmployees.add(Employee.builder().firstName("Anand").lastName("Kumar").email("anand@gmail.com").build());

        given(employeeServiceMock.getAllEmployees()).willReturn(listOfEmployees);

        // when
        ResultActions response = mockMvc.perform(get("/api/employees"));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(listOfEmployees.size())));
    }

    // get by id operation
    @Test
    @DisplayName("getEmployeeById")
    public void givenEmployeeId_whenGetEmployeeById_thenEmployee() throws Exception {
        // given
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Adarsh")
                .lastName("Kumar")
                .email("adarsh@gmail.com")
                .build();

        given(employeeServiceMock.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

        // when
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    // get by id operation - return empty
    @Test
    @DisplayName("getEmployeeById")
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenEmpty() throws Exception {
        // given
        long employeeId = 1L;

        given(employeeServiceMock.getEmployeeById(employeeId)).willReturn(Optional.empty());

        // when
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        // then
        response.andExpect(status().isNotFound()).andDo(print());
    }

    // update operation
    @Test
    @DisplayName("updateEmployee")
    public void givenEmployeeObject_whenUpdateEmployee_thenUpdatedEmployee() throws Exception {
        // given
        long employeeId = 1L;
        Employee updatedEmployee = Employee.builder()
                .firstName("Anand")
                .lastName("K")
                .email("anand@gmail.com")
                .build();

        // whatever argument is passed to function is returned
        given(employeeServiceMock.updateEmployee(any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())));
    }

    // delete operation
    @Test
    @DisplayName("deleteEmployee")
    public void givenEmployeeId_whenDeleteEmployee_thenCheckEmployeeDeletion() throws Exception {
        // given
        long employeeId = 1L;
        willDoNothing().given(employeeServiceMock).deleteEmployee(employeeId);

        // when
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", employeeId));

        // then
        response.andExpect(status().isOk())
                .andDo(print());
    }
}
