## Notes:

###### 01. add entity, repository and its tests

###### Repository Testing

1. `@DataJpaTest` annotation
   - to test the persistence layer that will autoconfigure in-memory embedded database for testing
   - scans `@Entiry` classes and Spring Data JPA repositories
   - doesn't load other Spring beans (`@Components`, `@Service` etc.) into Application Context
   - tests are transactional. So manually roll back at the end of each test
   - can also be used to test `DataSource`, `JDBCTemplate`, `EntityManager`
2. `@BeforeEach` annotation
3. save operation
4. get all operation
5. get by id operation
6. custom query
7. delete operation
8. custom query using JPQL with _**indexed**_ parameters(using `@Query` annotation)
9. custom query using JPQL with _**named**_ parameters(using `@Query` annotation)
10. custom native query using SQL with _**indexed**_ parameters(using `@Query` annotation
11. custom native query using SQL with _**named**_ parameters(using `@Query` annotation)

###### 02. add service methods and its tests, exception

###### Mockito

1. Mockito basics
   - `Mockito.mock()` -> To create a mock object of a given class or interface
   - `@Mock` 
     - To mock an object 
     - Useful when mocked object is used at multiple places to avoid calling mock() method multiple times.
   - `@InjectMocks`
     - When we want to inject a mocked object into another mocked object
     - Creates the mock object of the class and injects the mocks that are marked with the annotations @Mock into it 
2. BDDMockito Class
   - Mockito library is shipped with a BDDMockito class which introduces BDD-friendly APIs
   - Mockito vs. BDDMockito
     - traditional mocking in Mockito -> `Mockito.when(obj).thenReturn()`
     - BDD friendly approach with BDDMockito -> `BDDMockito.given().willReturn()`

###### Service Testing

1. Unit test for service methods
2. Unit test for service methods for Exception


