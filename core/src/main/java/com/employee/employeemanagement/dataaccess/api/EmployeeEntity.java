package com.employee.employeemanagement.dataaccess.api;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.employee.employeemanagement.common.api.Employee;
import com.employee.general.dataaccess.api.ApplicationPersistenceEntity;

/**
 * An Employee entity class.
 */
@Entity
@Table(name = "Employee")
public class EmployeeEntity extends ApplicationPersistenceEntity implements Employee {

  private static final long serialVersionUID = 1L;

  private String name;

  private Integer age;

  private String location;

  private Boolean validEmployee = false;

  /**
   * @return name
   */
  @Override
  public String getName() {

    return this.name;
  }

  /**
   * @param name new value of {@link #getname}.
   */
  @Override
  public void setName(String name) {

    this.name = name;
  }

  /**
   * @return age //
   */
  @Override
  public Integer getAge() {

    return this.age;
  }

  /**
   * @param age new value of {@link #getage}.
   */
  @Override
  public void setAge(Integer age) {

    this.age = age;
  }

  /**
   * @return location
   */
  @Override
  public String getLocation() {

    return this.location;
  }

  /**
   * @param location new value of {@link #getlocation}.
   */
  @Override
  public void setLocation(String location) {

    this.location = location;
  }

  /**
   * @return validEmployee
   */
  @Override
  public Boolean getValidEmployee() {

    return this.validEmployee;
  }

  /**
   * @param validEmployee new value of {@link #getvalidEmployee}.
   */
  @Override
  public void setValidEmployee(Boolean validEmployee) {

    this.validEmployee = validEmployee;
  }

  /**
   * @return serialversionuid
   */
  public static long getSerialversionuid() {

    return serialVersionUID;
  }

  /**
   * The constructor.
   *
   * @param name the name
   * @param age the age
   * @param location the location
   * @param validEmployee isValidEmployee
   */
  public EmployeeEntity(String name, Integer age, String location, Boolean validEmployee) {

    super();
    this.name = name;
    this.age = age;
    this.location = location;
    this.validEmployee = validEmployee;
  }

  /**
   * The constructor.
   */
  public EmployeeEntity() {

    super();
  }

  @Override
  public String toString() {

    return "EmployeeEntity [name=" + this.name + ", age=" + this.age + ", location=" + this.location
        + ", validEmployee=" + this.validEmployee + "]";
  }

}
