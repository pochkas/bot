package io.project.AviaticketsBot.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class Test {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private Data empService = new EmployeeService();

    @Test
    public void givenMockingIsDoneByMockito_whenGetIsCalled_shouldReturnMockedObject() {
        Employee emp = new Employee(“E001”, "Eric Simmons");
        Mockito
                .when(restTemplate.getForEntity(
                        “http://localhost:8080/employee/E001”, Employee.class))
          .thenReturn(new ResponseEntity(emp, HttpStatus.OK));

        Employee employee = empService.getEmployee(id);
        Assertions.assertEquals(emp, employee);
    }
}
