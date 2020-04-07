package com.employee.employeemanagement.service.impl.rest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devonfw.module.kafka.common.messaging.retry.api.RetryState;
import com.devonfw.module.service.common.api.client.config.ServiceClientConfigBuilder;
import com.employee.employeemanagement.logic.api.to.EmployeeEto;
import com.employee.employeemanagement.logic.api.to.EmployeeSearchCriteriaTo;
import com.employee.employeemanagement.service.api.rest.EmployeemanagementRestService;
import com.employee.general.service.base.test.RestServiceTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @param <V>
 * @param <K>
 * @param <V>
 *
 */
@ExtendWith(SpringExtension.class)
public class EmployeeManagementKafkaServiceImplTest extends RestServiceTest {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(EmployeeManagementKafkaServiceImplTest.class);

  private ProducerRecord<Object, Object> producerRecord;

  /**
   *
   */
  @Test
  public void saveEmployeeViaKafkaService() {

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

    this.producerRecord = new ProducerRecord<>("sample-employee-topic", 0, "employee", convertedMessage);

    setHeaders(this.producerRecord);

    EmployeemanagementRestService employeemanagementRestService = getServiceClientFactory()
        .create(EmployeemanagementRestService.class, new ServiceClientConfigBuilder().host("localhost").authBasic()
            .userLogin("manager").userPassword("manager").buildMap());

    // Act
    getMessageSender().sendMessage(this.producerRecord);

    // Assert
    EmployeeSearchCriteriaTo employeCriteria = new EmployeeSearchCriteriaTo();
    employeCriteria.setName(employee.getName());
    employeCriteria.setLocation(employee.getLocation());

    Page<EmployeeEto> fetchedEmployee = employeemanagementRestService.findEmployees(employeCriteria);

    Awaitility.await().until(() -> fetchedEmployee.isEmpty() == false);

  }

  /**
  *
  */
  @Test
  public void deleteEmployeeViaKafkaService() {

    // Arrange
    EmployeeEto employee = new EmployeeEto();
    employee.setAge(24);
    employee.setLocation("Chennai,India");
    employee.setName("Ashwin");
    employee.setValidEmployee(false);

    EmployeemanagementRestService employeemanagementRestService = getServiceClientFactory()
        .create(EmployeemanagementRestService.class, new ServiceClientConfigBuilder().host("localhost").authBasic()
            .userLogin("manager").userPassword("manager").buildMap());

    employeemanagementRestService.saveEmployee(employee);

    EmployeeSearchCriteriaTo employeCriteria = new EmployeeSearchCriteriaTo();
    employeCriteria.setName(employee.getName());
    employeCriteria.setLocation(employee.getLocation());

    Page<EmployeeEto> fetchedEmployee = employeemanagementRestService.findEmployees(employeCriteria);

    ProducerRecord<String, String> record = new ProducerRecord<>("delete-Employee", 0, "employeeId",
        fetchedEmployee.getContent().get(0).getId().toString());

    setRetryHeaders(record);

    // Act
    getMessageSender().sendMessage(record);

    // Assert
    Awaitility.await().until(() -> employeemanagementRestService.findEmployees(employeCriteria).isEmpty() == true);

  }

  private <K, V> void setHeaders(ProducerRecord<K, V> producerRecord) {

    // can add more custom headers

    // Retry headers
    setRetryHeaders(producerRecord);
  }

  private <K, V> void setRetryHeaders(ProducerRecord<K, V> producerRecord) {

    Instant today = Instant.now();

    // value for the headers.
    getMessageRetryContext().setRetryCount(2);
    getMessageRetryContext().setRetryNext(Instant.parse(today.toString()));
    getMessageRetryContext().setRetryReadCount(1);
    getMessageRetryContext().setRetryUntil(Instant.parse(today.plus(15, ChronoUnit.DAYS).toString()));
    getMessageRetryContext().setRetryState(RetryState.PENDING);

    // injecting retry headers.
    getMessageRetryContext().injectInto(producerRecord);
  }

}
