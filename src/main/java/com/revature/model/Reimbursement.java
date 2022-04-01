package com.revature.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

public class Reimbursement {

  private int id;
  private int remitAmount;
  private String remitSubmitted;
  private String remitResolved;
  private String remitDescription;
  private User employee;
  private User manager;
  private int type;
  private int status;

  public Reimbursement(int id, int remitAmount, String remitDescription, String remitSubmitted, String remitResolved, User employee, User manager, int type, int status) {
    this.id = id;
    this.remitAmount = remitAmount;
    this.remitDescription = remitDescription;
    this.remitSubmitted = remitSubmitted;
    this.remitResolved = remitResolved;
    this.employee = employee;
    this.manager = manager;
    this.type = type;
    this.status = status;
  }

  public Reimbursement() {

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

  public String getRemitSubmitted() {
    return remitSubmitted;
  }

  public void setRemitSubmitted(String remitSubmitted) {
    this.remitSubmitted = remitSubmitted;
  }

  public String getRemitResolved() {
    return remitResolved;
  }

  public void setRemitResolved(String remitResolved) {
    this.remitResolved = remitResolved;
  }

  public String getRemitDescription() {
    return remitDescription;
  }

  public void setRemitDescription(String remitDescription) {
    this.remitDescription = remitDescription;
  }

  public String getRemitDrescription() {
    return remitDescription;
  }

  public void setRemitDrescription(String remitDescription) {
    this.remitDescription = Reimbursement.this.remitDescription;
  }

  public User getEmployee() {
    return employee;
  }

  public void setEmployee(User employee) {
    this.employee = employee;
  }

  public User getManager() {
    return manager;
  }

  public void setManager(User manager) {
    this.manager = manager;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Reimbursement remit = (Reimbursement) o;
    return id == remit.id && Objects.equals(remitAmount, remit.remitAmount) && Objects.equals(remitSubmitted, remit.remitSubmitted)
        && Objects.equals(remitResolved, remit.remitResolved) && Objects.equals(remitDescription, remit.remitDescription)
        && Objects.equals(employee, remit.employee) && Objects.equals(manager, remit.manager) && Objects.equals(type, remit.type)
        && Objects.equals(status, remit.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, remitAmount, remitSubmitted, remitResolved, remitDescription, employee, manager, type, status);
  }
  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", remitAmount='" + remitAmount + '\'' +
        ", remitSubmitted='" + remitSubmitted + '\'' +
        ", remitResolved='" + remitResolved + '\'' +
        ", remitDescription='" + remitDescription + '\'' +
        ", employee='" + employee + '\'' +
        ", manager='" + manager + '\'' +
        ", type='" + type + '\'' +
        ", status='" + status + '\'' +
        '}';
  }
}
