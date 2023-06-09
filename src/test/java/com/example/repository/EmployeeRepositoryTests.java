package com.example.repository;

import com.example.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setup() {
        employee = Employee.builder()
                .firstName("Adarsh")
                .lastName("Kumar")
                .email("adarsh@gmail.com")
                .build();
    }

    // save operation
    @Test
    @DisplayName("save")
    public void givenEmployeeObject_whenSave_thenSavedEmployee() {
        // given

        // when
        Employee savedEmployee = employeeRepository.save(employee);

        // then
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    // get all operation
    @Test
    @DisplayName("findAll")
    public void givenEmployeeList_whenFindAll_thenEmployeesList() {
        // given
        Employee employee1 = Employee.builder()
                .firstName("Anand")
                .lastName("Kumar")
                .email("anand@gmail.com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        // when
        List<Employee> employeesList = employeeRepository.findAll();

        // then
        assertThat(employeesList).isNotNull();
        assertThat(employeesList.size()).isEqualTo(2);
    }

    // get by id operation
    @Test
    @DisplayName("findById")
    public void givenEmployeeId_whenFindById_thenEmployee(){
        // given
        employeeRepository.save(employee);

        // when
        Employee employeeDB = employeeRepository.findById(employee.getId()).orElse(null);

        // then
        assertThat(employeeDB).isNotNull();
    }

    // custom query
    @Test
    @DisplayName("findByEmail")
    public void givenEmployeeEmail_whenFindByEmail_thenEmployee(){
        // given
        employeeRepository.save(employee);

        // when
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).orElse(null);

        // then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    // delete operation
    @Test
    @DisplayName("deleteById")
    public void givenEmployeeId_whenDeleteById_thenCheckEmployeeDeletion() {
        // given
        employeeRepository.save(employee);

        // when
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        // then
        assertThat(employeeOptional).isEmpty();
    }

    // custom query JPQL with indexed parameter operation
    @Test
    @DisplayName("findByFullNameJPQLIndexed")
    public void givenFirstNameAndLastName_whenFindByFullNameJPQLIndexed_thenEmployee() {
        // given
        String firstName = "Adarsh";
        String lastName = "Kumar";
        employeeRepository.save(employee);

        // when
        Employee savedEmployee = employeeRepository.findByFullNameJPQLIndexed(firstName, lastName);

        // then
        assertThat(savedEmployee).isNotNull();
    }

    // custom query JPQL with named parameter operation
    @Test
    @DisplayName("findByFullNameJPQLNamed")
    public void givenFirstNameAndLastName_whenFindByFullNameJPQLNamed_thenEmployee() {
        // given
        String firstName = "Adarsh";
        String lastName = "Kumar";
        employeeRepository.save(employee);

        // when
        Employee savedEmployee = employeeRepository.findByFullNameJPQLNamed(firstName, lastName);

        // then
        assertThat(savedEmployee).isNotNull();
    }

    // custom native query SQL with indexed parameter operation
    @Test
    @DisplayName("findByFullNameSQLIndexed")
    public void givenFirstNameAndLastName_whenFindByFullNameSQLIndexed_thenEmployee() {
        // given
        String firstName = "Adarsh";
        String lastName = "Kumar";
        employeeRepository.save(employee);

        // when
        Employee savedEmployee = employeeRepository.findByFullNameSQLIndexed(firstName, lastName);

        // then
        assertThat(savedEmployee).isNotNull();
    }

    // custom native query SQL with named parameter operation
    @Test
    @DisplayName("findByFullNameSQLNamed")
    public void givenFirstNameAndLastName_whenFindByFullNameSQLNamed_thenEmployee() {
        // given
        String firstName = "Adarsh";
        String lastName = "Kumar";
        employeeRepository.save(employee);

        // when
        Employee savedEmployee = employeeRepository.findByFullNameSQLNamed(firstName, lastName);

        // then
        assertThat(savedEmployee).isNotNull();
    }
}
