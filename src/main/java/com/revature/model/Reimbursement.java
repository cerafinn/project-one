package com.revature.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Reimbursement {

  private int id;
  private int remitAmount;
  private Timestamp remitSubmitted;
  private Timestamp remitResolved;
  private String remitDrescription;
  //  reimb_receipt bytea,
  private User employee;
  private User manager;
  private String type;
  private String status;

  public Reimbursement(int id, int remitAmount, String remitDrescription, Timestamp remitSubmitted, Timestamp remitResolved, User employee, User manager, String type, String status) {
    this.id = id;
    this.remitAmount = remitAmount;
    this.remitDrescription = remitDrescription;
    this.remitSubmitted = remitSubmitted;
    this.remitResolved = remitResolved;
    this.employee = employee;
    this.manager = manager;
    this.type = type;
    this.status = status;
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

  public String getRemitDrescription() {
    return remitDrescription;
  }

  public void setRemitDrescription(String remitDrescription) {
    this.remitDrescription = remitDrescription;
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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Reimbursement remit = (Reimbursement) o;
    return id == remit.id && Objects.equals(remitAmount, remit.remitAmount) && Objects.equals(remitSubmitted, remit.remitSubmitted)
        && Objects.equals(remitResolved, remit.remitResolved) && Objects.equals(remitDrescription, remit.remitDrescription)
        && Objects.equals(employee, remit.employee) && Objects.equals(manager, remit.manager) && Objects.equals(type, remit.type)
        && Objects.equals(status, remit.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, remitAmount, remitSubmitted, remitResolved, remitDrescription, employee, manager, type, status);
  }
  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", remitAmount='" + remitAmount + '\'' +
        ", remitSubmitted='" + remitSubmitted + '\'' +
        ", remitResolved='" + remitResolved + '\'' +
        ", remitDescription='" + remitDrescription + '\'' +
        ", employee='" + employee + '\'' +
        ", manager='" + manager + '\'' +
        ", type='" + type + '\'' +
        ", status='" + status + '\'' +
        '}';
  }
}
