package com.employee.employeemanagement.service.impl.kafka;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

import com.devonfw.module.kafka.common.messaging.retry.api.client.MessageRetryOperations;

/**
 * @author ravicm
 * @param <K>
 * @param <V>
 *
 */
@Named
public class SaveEmployeeConsumer<K, V> {

  private static final Logger LOG = LoggerFactory.getLogger(SaveEmployeeConsumer.class);

  @Inject
  private MessageRetryOperations<K, V> messageRetryOperations;

  @Inject
  private SaveEmployeMessageProcessor<K, V> saveEmployeeMessageProcessor;

  /**
   * @param consumerRecord
   * @param acknowledgment
   * @throws Exception
   */
  @KafkaListener(topics = "employeeapp-employee-v1-add", groupId = "${messaging.kafka.consumer.groupId}", containerFactory = "kafkaListenerContainerFactory")
  public void consumer(ConsumerRecord<K, V> consumerRecord, Acknowledgment acknowledgment) {

    try {
      processMessageAndAcknowledgeListener(consumerRecord, acknowledgment);
    } catch (Exception e) {
      LOG.error("The Error while processing message: {} ", e);
    }
  }

  private void processMessageAndAcknowledgeListener(ConsumerRecord<K, V> consumerRecord,
      Acknowledgment acknowledgment) {

    this.messageRetryOperations.processMessageWithRetry(consumerRecord, this.saveEmployeeMessageProcessor);

    // Acknowledge the listener.
    acknowledgment.acknowledge();
  }
}
