package com.employee.general.common.api.security;

import javax.inject.Named;

import com.devonfw.module.security.common.base.accesscontrol.AccessControlConfig;

/**
 * Example of {@link AccessControlConfig} that used for testing.
 */
@Named
public class ApplicationAccessControlConfig extends AccessControlConfig {

  public static final String APP_ID = "samplekafkaapplication";

  private static final String PREFIX = APP_ID + ".";

  public static final String PERMISSION_SAVE_EMPLOYEE = PREFIX + "SaveEmployee";

  public static final String PERMISSION_DELETE_EMPLOYEE = PREFIX + "DeleteEmployee";

  public static final String GROUP_MANAGER = PREFIX + "manager";

  /**
   * The constructor.
   */
  public ApplicationAccessControlConfig() {

    super();
    group(GROUP_MANAGER, PERMISSION_SAVE_EMPLOYEE, PERMISSION_DELETE_EMPLOYEE);
  }

}