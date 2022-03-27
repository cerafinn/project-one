package com.revature.controller;

import com.revature.service.ReimbursementService;
import io.javalin.http.Handler;

public class ReimbursementController {
  public ReimbursementService reimbursementService;

  public ReimbursementController() { this.reimbursementService = new ReimbursementService(); }

  private Handler

  // endpoints for:
  //get all reimb
  //get all by uid
  //add reimb
  //update reimb (resolve from pending)
  //view receipt

}
