package com.employee.employeemanagement.service.impl.rest;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devonfw.module.kafka.common.messaging.api.client.MessageSender;
import com.devonfw.module.security.common.api.authentication.DefaultAuthentication;
import com.devonfw.module.security.jwt.common.base.JwtConstants;
import com.devonfw.module.service.common.api.client.config.ServiceClientConfigBuilder;
import com.employee.employeemanagement.logic.api.to.EmployeeEto;
import com.employee.employeemanagement.logic.api.to.EmployeeSearchCriteriaTo;
import com.employee.employeemanagement.service.api.rest.EmployeemanagementRestService;
import com.employee.employeemanagement.service.impl.kafka.DeleteEmployeeMessageConsumer;
import com.employee.employeemanagement.service.impl.kafka.SaveEmployeeConsumer;
import com.employee.general.service.base.test.RestServiceTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is a test class to test the {@link DeleteEmployeeMessageConsumer} and {@link SaveEmployeeConsumer}. Uses
 * {@link Awaitility} to test result for the asynchronous process of consuming messages.
 */
@ExtendWith(SpringExtension.class)
public class EmployeeManagementKafkaServiceImplTest extends RestServiceTest {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(EmployeeManagementKafkaServiceImplTest.class);

  /**
   * THis test method sends data of an employee Object and also a employeeId to the kafka broker using the
   * devon-kafka-framework's {@link MessageSender}. The Listener classes {@link SaveEmployeeConsumer} and
   * {@link DeleteEmployeeMessageConsumer} consumes the message process it to save and delete the message in DB. The
   * {@link Awaitility} is used to test the results of saving and deletion of employee in DB.
   */
  @Test
  public void saveAndDeleteEmployeeViaKafkaService() {

    // Step 1: Add new employee via Kafka
    // Arrange
    EmployeeEto employee = new EmployeeEto();
    employee.setAge(24);
    employee.setLocation("Chennai,India");
    employee.setName("Ashwin");
    employee.setValidEmployee(false);

    String convertedMessage = null;
    try {
      convertedMessage = new ObjectMapper().writer().writeValueAsString(employee);
    } catch (JsonProcessingException e) {
      LOG.error("Error while converting employee as String");
    }

    String jwtToken = createJwtToken();

    ProducerRecord<String, String> producerRecord = new ProducerRecord<>("employeeapp-employee-v1-add",
        convertedMessage);

    // adding jwt header
    producerRecord.headers().add(JwtConstants.HEADER_AUTHORIZATION, jwtToken.getBytes(Charset.forName("UTF-8")));

    EmployeemanagementRestService employeemanagementRestService = getServiceClientFactory()
        .create(EmployeemanagementRestService.class, new ServiceClientConfigBuilder().host("localhost").authBasic()
            .userLogin("manager").userPassword("manager").buildMap());

    // Act
    getMessageSender().sendMessage(producerRecord);

    // Assert
    EmployeeSearchCriteriaTo employeCriteria = new EmployeeSearchCriteriaTo();
    employeCriteria.setName(employee.getName());
    employeCriteria.setLocation(employee.getLocation());

    Awaitility.await()
        .until(() -> employeemanagementRestService.findEmployees(employeCriteria).getTotalElements() == 1);

    // Step 2: Delete employee via Kafka
    // Arrange
    EmployeeEto newEmployee = employeemanagementRestService.findEmployees(employeCriteria).getContent().get(0);

    producerRecord = new ProducerRecord<>("employeeapp-employee-v1-delete", newEmployee.getId().toString());
    // adding jwt header
    producerRecord.headers().add(JwtConstants.HEADER_AUTHORIZATION, jwtToken.getBytes(Charset.forName("UTF-8")));

    // Act
    getMessageSender().sendMessage(producerRecord);
    // Assert
    Awaitility.await().until(() -> employeemanagementRestService.findEmployees(employeCriteria).isEmpty() == true);
  }

  private String createJwtToken() {

    List<SimpleGrantedAuthority> grantedAuthorities = Arrays.asList(new SimpleGrantedAuthority("admin"));

    Authentication authentication = new DefaultAuthentication("ashwin", "*****", grantedAuthorities);

    return getJwtCreator().create(authentication);
  }

}
