package com.revature.dto;

import java.io.InputStream;
import java.util.Objects;

public class AddReimbursementDTO {

  private int remitAmount;
  private String remitDescription;
  private int remitType;
  private InputStream receipt;

  public AddReimbursementDTO() {}

  public AddReimbursementDTO(int remitAmount, String remitDescription, int remitType, InputStream receipt) {
    this.remitAmount = remitAmount;
    this.remitDescription = remitDescription;
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
    AddReimbursementDTO reimb = (AddReimbursementDTO) o;
    return Objects.equals(remitAmount, reimb.remitAmount) && Objects.equals(remitDescription, reimb.remitDescription) && Objects.equals(remitType, reimb.remitType)
        && Objects.equals(receipt, reimb.receipt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(remitAmount, remitDescription, remitType, receipt);
  }

  @Override
  public String toString() {
    return "addReimbursementDTO{" +
        "remitAmount=" + remitAmount +
        ", remitDescription='" + remitDescription + '\'' +
        ", remitType='" + remitType + '\'' +
        ", receipt=" + receipt +
        '}';
  }
}
