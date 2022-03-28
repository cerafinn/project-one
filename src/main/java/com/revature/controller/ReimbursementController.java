package com.revature.controller;

import com.revature.dto.ResolveReimbursementDTO;
import com.revature.model.Reimbursement;
import com.revature.service.ReimbursementService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;

public class ReimbursementController implements Controller {
  public ReimbursementService reimbursementService;

  public ReimbursementController() { this.reimbursementService = new ReimbursementService(); }

  private Handler getallReimbursements = ctx -> {
    List<ResolveReimbursementDTO> reimbursements = reimbursementService.getAllReimbursements();
    ctx.json(reimbursements);
  };

  // endpoints for:
  //get all reimb
  //get all by uid
  //add reimb
  //update reimb (resolve from pending)
  //view receipt
  @Override
  public void mapEndpoints(Javalin app) {

  }



}
