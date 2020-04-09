package com.employee.employeemanagement.logic.api.to;

import com.devonfw.module.basic.common.api.to.AbstractEto;
import com.employee.employeemanagement.common.api.Employee;

/**
 * Entity transport object of Employee
 */
public class EmployeeEto extends AbstractEto implements Employee {

  private static final long serialVersionUID = 1L;

  private String name;

  private Integer age;

  private String location;

  private Boolean validEmployee;

  @Override
  public String getName() {

    return this.name;
  }

  /**
   * The constructor.
   */
  public EmployeeEto() {

    super();
  }

  @Override
  public void setName(String name) {

    this.name = name;
  }

  @Override
  public Integer getAge() {

    return this.age;
  }

  @Override
  public void setAge(Integer age) {

    this.age = age;
  }

  @Override
  public String getLocation() {

    return this.location;
  }

  @Override
  public void setLocation(String location) {

    this.location = location;
  }

  @Override
  public Boolean getValidEmployee() {

    return this.validEmployee;
  }

  @Override
  public void setValidEmployee(Boolean validEmployee) {

    this.validEmployee = validEmployee;
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
    result = prime * result + ((this.age == null) ? 0 : this.age.hashCode());
    result = prime * result + ((this.location == null) ? 0 : this.location.hashCode());
    result = prime * result + ((this.validEmployee == null) ? 0 : this.validEmployee.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    // class check will be done by super type EntityTo!
    if (!super.equals(obj)) {
      return false;
    }
    EmployeeEto other = (EmployeeEto) obj;
    if (this.name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!this.name.equals(other.name)) {
      return false;
    }
    if (this.age == null) {
      if (other.age != null) {
        return false;
      }
    } else if (!this.age.equals(other.age)) {
      return false;
    }
    if (this.location == null) {
      if (other.location != null) {
        return false;
      }
    } else if (!this.location.equals(other.location)) {
      return false;
    }
    if (this.validEmployee == null) {
      if (other.validEmployee != null) {
        return false;
      }
    } else if (!this.validEmployee.equals(other.validEmployee)) {
      return false;
    }
    return true;
  }

}
