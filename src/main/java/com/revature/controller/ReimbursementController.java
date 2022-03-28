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

  private Handler getReimbursementsByUser;

  private Handler addReimburement;

  private Handler getReimbReceipt;

  private Handler resolveReimbursement;

  @Override
  public void mapEndpoints(Javalin app) {
  app.get("/reimbursements", getallReimbursements);
  app.get("/users/{userid}/reimbursements", getReimbursementsByUser);
  app.post("/reimbursements", addReimburement);
  app.get("reimbursements/{reimbId}/receipt", getReimbReceipt);
  app.patch("reimbursements/{reimbId}", resolveReimbursement);
  }



}
