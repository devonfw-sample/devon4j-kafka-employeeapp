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
 * @param <K>
 * @param <V>
 *
 */
@Named
public class DeleteEmployeeMessageConsumer<K, V> {

  private static final Logger LOG = LoggerFactory.getLogger(DeleteEmployeeMessageConsumer.class);

  @Inject
  private MessageRetryOperations<K, V> messageRetryOperations;

  @Inject
  private DeleteEmployeeMessageProcessor<K, V> deleteEmployeeMessageProcessor;

  /**
   * @param consumerRecord
   * @param acknowledgment
   */
  @KafkaListener(topics = "delete-Employee", groupId = "sample-Delete", containerFactory = "kafkaListenerContainerFactory")
  public void consumer(ConsumerRecord<K, V> consumerRecord, Acknowledgment acknowledgment) {

    try {
      processMessageAndAcknowledgeListener(consumerRecord, acknowledgment);
    } catch (Exception e) {
      LOG.error("The Error while processing message: {} ", e);
    }
  }

  private void processMessageAndAcknowledgeListener(ConsumerRecord<K, V> consumerRecord,
      Acknowledgment acknowledgment) {

    this.messageRetryOperations.processMessageWithRetry(consumerRecord, this.deleteEmployeeMessageProcessor);

    // Acknowledge the listener.
    acknowledgment.acknowledge();
  }

}
