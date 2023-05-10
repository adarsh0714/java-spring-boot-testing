## Notes:

###### 01. add entity, repository and its tests

###### Repository Testing

1. `@DataJpaTest` annotation
   - to test the persistence layer that will autoconfigure in-memory embedded database for testing
   - scans `@Entiry` classes and Spring Data JPA repositories
   - doesn't load other Spring beans (`@Components`, `@Service` etc) into Application Context
   - tests are transactional. So manually roll back at the end of each test
   - can also be used to test `DataSource`, `JDBCTemplate`, `EntityManager`
2. save operation
3. get all operation
4. get by id operation
5. custom query
6. update operation
7. delete operation
8. custom query using JPQL with _**indexed**_ parameters(using `@Query` annotation)
9. custom query using JPQL with _**named**_ parameters(using `@Query` annotation)
10. custom native query using SQL with _**indexed**_ parameters(using `@Query` annotation
11. custom native query using SQL with _**named**_ parameters(using `@Query` annotation)
12. `@BeforeEach` annotation

