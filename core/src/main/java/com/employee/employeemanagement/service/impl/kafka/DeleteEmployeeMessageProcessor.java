package com.employee.employeemanagement.service.impl.kafka;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.LoggerFactory;

import com.devonfw.module.kafka.common.messaging.retry.api.client.MessageProcessor;
import com.employee.employeemanagement.logic.api.Employeemanagement;

import ch.qos.logback.classic.Logger;

/**
 * @param <K>
 * @param <V>
 *
 */
@Named
public class DeleteEmployeeMessageProcessor<K, V> implements MessageProcessor<K, V> {

  private static final Logger LOG = (Logger) LoggerFactory.getLogger(DeleteEmployeeMessageProcessor.class);

  @Inject
  private Employeemanagement employeemanagement;

  @Override
  public void processMessage(ConsumerRecord<K, V> message) {

    long employeeId = 0L;
    try {
      employeeId = Long.parseLong(message.value().toString());
    } catch (Exception e) {
      LOG.warn("Message conversion failed and it will be ignored", e);
    }

    this.employeemanagement.deleteEmployee(employeeId);
  }

}
