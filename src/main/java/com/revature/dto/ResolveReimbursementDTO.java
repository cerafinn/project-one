package com.revature.dto;

import com.revature.model.User;

import java.sql.Timestamp;
import java.util.Objects;

public class ResolveReimbursementDTO {

  private int id;
  private int remitAmount;
  private Timestamp remitSubmitted;
  private Timestamp remitResolved;
  private String remitDescription;
  private int type;
  private int status;

  private String employeeUsername;
  private String managerUsername;

  public ResolveReimbursementDTO() {}

  public ResolveReimbursementDTO(int id, int remitAmount, String remitDescription, Timestamp remitSubmitted, Timestamp remitResolved, int type, int status, String employeeUsername, String managerUsername) {
    this.id = id;
    this.remitAmount = remitAmount;
    this.remitDescription = remitDescription;
    this.remitSubmitted = remitSubmitted;
    this.remitResolved = remitResolved;
    this.type = type;
    this.status = status;
    this.employeeUsername = employeeUsername;
    this.managerUsername = managerUsername;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getRemitAmount() {
    return remitAmount;
  }

  public void setRemitAmount(int remitAmount) {
    this.remitAmount = remitAmount;
  }

  public Timestamp getRemitSubmitted() {
    return remitSubmitted;
  }

  public void setRemitSubmitted(Timestamp remitSubmitted) {
    this.remitSubmitted = remitSubmitted;
  }

  public Timestamp getRemitResolved() {
    return remitResolved;
  }

  public void setRemitResolved(Timestamp remitResolved) {
    this.remitResolved = remitResolved;
  }

  public String getRemitDescription() {
    return remitDescription;
  }

  public void setRemitDescription(String remitDescription) {
    this.remitDescription = remitDescription;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getEmployeeUsername() {
    return employeeUsername;
  }

  public void setEmployeeUsername(String employeeUsername) {
    this.employeeUsername = employeeUsername;
  }

  public String getManagerUsername() {
    return managerUsername;
  }

  public void setManagerUsername(String managerUsername) {
    this.managerUsername = managerUsername;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ResolveReimbursementDTO)) return false;
    ResolveReimbursementDTO that = (ResolveReimbursementDTO) o;
    return id == that.id && remitAmount == that.remitAmount && type == that.type && status == that.status && remitSubmitted.equals(that.remitSubmitted) && Objects.equals(remitResolved, that.remitResolved) && remitDescription.equals(that.remitDescription) && employeeUsername.equals(that.employeeUsername) && Objects.equals(managerUsername, that.managerUsername);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, remitAmount, remitSubmitted, remitResolved, remitDescription, type, status, employeeUsername, managerUsername);
  }

  @Override
  public String toString() {
    return "ResolveReimbursementDTO{" +
        "id=" + id +
        ", remitAmount=" + remitAmount +
        ", remitSubmitted=" + remitSubmitted +
        ", remitResolved=" + remitResolved +
        ", remitDescription='" + remitDescription + '\'' +
        ", type=" + type +
        ", status=" + status +
        ", employeeUsername='" + employeeUsername + '\'' +
        ", managerUsername='" + managerUsername + '\'' +
        '}';
  }
}
