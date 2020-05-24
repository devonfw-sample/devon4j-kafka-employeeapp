package com.employee.general.service.base.test;

import javax.inject.Inject;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import com.devonfw.module.kafka.common.messaging.api.client.MessageSender;
import com.devonfw.module.security.jwt.common.api.JwtCreator;
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
 */
@SpringBootTest(classes = { SpringBootApp.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class RestServiceTest extends SubsystemDbTest {

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
  private MessageSender<String, String> messageSender;

  @Inject
  private JwtCreator jwtCreator;

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
  protected MessageSender<String, String> getMessageSender() {

    return this.messageSender;
  }

  /**
   * @return jwtCreator
   */
  public JwtCreator getJwtCreator() {

    return jwtCreator;
  }

}
