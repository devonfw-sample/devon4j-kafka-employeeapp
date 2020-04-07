package com.employee.employeemanagement.service.impl.kafka;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.LoggerFactory;

import com.devonfw.module.kafka.common.messaging.retry.api.client.MessageProcessor;
import com.employee.employeemanagement.logic.api.Employeemanagement;
import com.employee.employeemanagement.logic.api.to.EmployeeEto;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Logger;

/**
 * @param <K> the key type
 * @param <V> the value type
 *
 */
@Named
public class SaveEmployeMessageProcessor<K, V> implements MessageProcessor<K, V> {

  private static final Logger LOG = (Logger) LoggerFactory.getLogger(SaveEmployeMessageProcessor.class);

  @Inject
  private Employeemanagement employeemanagement;

  @Override
  public void processMessage(ConsumerRecord<K, V> message) {

    EmployeeEto convertedValue = null;
    try {
      convertedValue = new ObjectMapper().readValue(message.value().toString(), EmployeeEto.class);
    } catch (Exception e) {
      LOG.warn("Message conversion failed. Message will be ignored.", e);
    }
    if (convertedValue != null) {
      this.employeemanagement.saveEmployee(convertedValue);
    }
  }

}
