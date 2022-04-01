package com.revature.dto;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Objects;

public class AddReimbursementDTO {

  private int remitAmount;
  private String remitDescription;
  private String remitSubmitted;
  private int remitType;
  private InputStream receipt;

  public AddReimbursementDTO() {}

  public AddReimbursementDTO(int remitAmount, String remitDescription, String remitSubmitted, int remitType, InputStream receipt) {
    this.remitAmount = remitAmount;
    this.remitDescription = remitDescription;
    this.remitSubmitted = remitSubmitted;
    this.remitType = remitType;
    this.receipt = receipt;
  }

  public int getRemitAmount() {
    return remitAmount;
  }

  public void setRemitAmount(int remitAmount) {
    this.remitAmount = remitAmount;
  }

  public String getRemitDescription() {
    return remitDescription;
  }

  public void setRemitDescription(String remitDescription) {
    this.remitDescription = remitDescription;
  }

  public String getRemitSubmitted() {
    return remitSubmitted;
  }

  public void setRemitSubmitted(String remitSubmitted) {
    this.remitSubmitted = remitSubmitted;
  }

  public int getRemitType() {
    return remitType;
  }

  public void setRemitType(int remitType) {
    this.remitType = remitType;
  }

  public InputStream getReceipt() {
    return receipt;
  }

  public void setReceipt(InputStream receipt) {
    this.receipt = receipt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AddReimbursementDTO)) return false;
    AddReimbursementDTO that = (AddReimbursementDTO) o;
    return remitAmount == that.remitAmount && remitType == that.remitType && remitDescription.equals(that.remitDescription) && remitSubmitted.equals(that.remitSubmitted) && Objects.equals(receipt, that.receipt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(remitAmount, remitDescription, remitSubmitted, remitType, receipt);
  }

  @Override
  public String toString() {
    return "AddReimbursementDTO{" +
        "remitAmount=" + remitAmount +
        ", remitDescription='" + remitDescription + '\'' +
        ", remitSubmitted='" + remitSubmitted + '\'' +
        ", remitType=" + remitType +
        ", receipt=" + receipt +
        '}';
  }
}
