package com.example.repository;

import com.example.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    // custom query using JPQL with index params
    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Employee findByFullNameJPQLIndexed(String firstName, String lastName);

    // custom query using JPQL with named params
    @Query("select e from Employee e where e.firstName =:firstName and e.lastName =:lastName")
    Employee findByFullNameJPQLNamed(@Param("firstName") String firstName, @Param("lastName") String lastName);

    // custom native query using SQL with index params
    @Query(value = "select * from employees e where e.first_name = ?1 and e.last_name = ?2", nativeQuery = true)
    Employee findByFullNameSQLIndexed(String firstName, String lastName);

    // custom native query using SQL with named params
    @Query(value = "select * from employees e where e.first_name =:firstName and e.last_name =:lastName", nativeQuery = true)
    Employee findByFullNameSQLNamed(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
