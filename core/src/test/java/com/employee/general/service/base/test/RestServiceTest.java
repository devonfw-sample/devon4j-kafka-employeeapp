package com.employee.general.service.base.test;

import javax.inject.Inject;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import com.devonfw.module.kafka.common.messaging.api.client.MessageSender;
import com.devonfw.module.kafka.common.messaging.retry.impl.MessageRetryContext;
import com.devonfw.module.service.common.api.client.ServiceClientFactory;
import com.devonfw.module.test.common.base.SubsystemDbTest;
import com.devonfw.module.test.common.base.SubsystemTest;
import com.employee.SpringBootApp;
import com.employee.general.common.base.test.DbTestHelper;
import com.employee.general.common.base.test.TestUtil;

/**
 * Abstract base class for {@link SubsystemTest}s which runs the tests within a local server. <br/>
 * <br/>
 * The local server's port is randomly assigned.
 *
 * @param <K>
 *
 * @param <V>
 */
@SpringBootTest(classes = { SpringBootApp.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class RestServiceTest<K, V> extends SubsystemDbTest {

  /**
   * The port of the web server during the test.
   */
  @LocalServerPort
  protected int port;

  @Inject
  private ServiceClientFactory serviceClientFactory;

  @Inject
  private DbTestHelper dbTestHelper;

  @Inject
  private MessageSender<K, V> messageSender;

  @Inject
  private MessageRetryContext messageRetryContext;

  @Override
  protected void doSetUp() {

    super.doSetUp();
  }

  @Override
  protected void doTearDown() {

    super.doTearDown();
    TestUtil.logout();
  }

  /**
   * @return the {@link DbTestHelper}
   */
  protected DbTestHelper getDbTestHelper() {

    return this.dbTestHelper;
  }

  /**
   * @return the {@link ServiceClientFactory}
   */
  protected ServiceClientFactory getServiceClientFactory() {

    return this.serviceClientFactory;
  }

  /**
   * @return messageSender
   */
  protected MessageSender<K, V> getMessageSender() {

    return this.messageSender;
  }

  /**
   * @return messageRetryContext
   */
  protected MessageRetryContext getMessageRetryContext() {

    return this.messageRetryContext;
  }
}
