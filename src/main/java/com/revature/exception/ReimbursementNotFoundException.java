package com.revature.exception;

public class ReimbursementNotFoundException extends Exception {
  // when a user has no reimbursements
  public ReimbursementNotFoundException(String message) {
    super(message);
  }
}
