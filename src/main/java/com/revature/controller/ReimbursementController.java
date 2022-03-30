package com.revature.controller;

import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.ResolveReimbursementDTO;
import com.revature.service.JWTService;
import com.revature.service.ReimbursementService;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.http.UploadedFile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.tika.Tika;

import java.io.InputStream;
import java.util.List;

public class ReimbursementController implements Controller {
  private ReimbursementService reimbursementService;
  private JWTService jwtService;

  public ReimbursementController() {
    this.reimbursementService = new ReimbursementService();
    this.jwtService = JWTService.getInstance();
  }

  private Handler getallReimbursements = (ctx) -> {
    if(ctx.header("Authorization") == null) {
      throw new UnauthorizedResponse("You must be logged in to view reimbursements");
    }

    String jwt = ctx.header("Authorization").split(" ")[1];
    Jws<Claims> token = this.jwtService.parseJwt(jwt);

    if(!token.getBody().get("user_role").equals("finance manager")) {
      throw new UnauthorizedResponse("You are not a finance manager");
    }

    List<ResolveReimbursementDTO> reimbursements = reimbursementService.getAllReimbursements();
    ctx.json(reimbursements);
  };

  private Handler getReimbursementsByUser = (ctx) -> {
    if(ctx.header("Authorization") == null) {
      throw new UnauthorizedResponse("You must be logged in to view reimbursements");
    }

    String jwt = ctx.header("Authorization").split(" ")[1];
    Jws<Claims> token = this.jwtService.parseJwt(jwt);

    //change this so that managers can filter by employee?
    if(!token.getBody().get("user_role").equals("employee")) {
      throw new UnauthorizedResponse("Unable to return information for a single employee");
    }

    String userId = ctx.pathParam("user_id");
    int id = Integer.parseInt(userId);
    if(!token.getBody().get("user_id").equals(id)) {
      throw new UnauthorizedResponse("You can only retrieve your own reimbursements");
    }

    List<ResolveReimbursementDTO> dtos = this.reimbursementService.getReimbByUser(id);
    ctx.json(dtos);
  };

  private Handler addReimburement =  (ctx) -> {
    if(ctx.header("Authorization") == null) {
      throw new UnauthorizedResponse("You must be logged in to view reimbursements");
    }

    String jwt = ctx.header("Authorization").split(" ")[1];
    Jws<Claims> token = this.jwtService.parseJwt(jwt);

    if(!token.getBody().get("user_role").equals("employee")) {
      throw new UnauthorizedResponse("You need to be a non-manager to submit a reimbursement request");
    }

    String uId = ctx.pathParam("user_id");
    int id = Integer.parseInt(uId);

    if(!token.getBody().get("user_id").equals(id)) {
      throw new UnauthorizedResponse("You can only submit your own reimbursement requests");
    }

    //add information for reimbursement
//    int remitAmount, String remitDescription, int remitType
    String rAmount = ctx.formParam("remitAmount");
    int remitAmount = Integer.parseInt(rAmount);
    String remitDescription = ctx.formParam("remitDescription");
    String rType = ctx.formParam("remitType");
    int remitType = Integer.parseInt(rType);

    AddReimbursementDTO dto = new AddReimbursementDTO();
    dto.setRemitAmount(remitAmount);
    dto.setRemitDescription(remitDescription);
    dto.setRemitType(remitType);

    UploadedFile upload = ctx.uploadedFile("receipt");
    InputStream receipt = upload.getContent() ;
    dto.setReceipt(receipt);

    ResolveReimbursementDTO reimbursement = this.reimbursementService.addNewReimb(id, dto);
    ctx.json(reimbursement);
  };

  private Handler getReimbReceipt = (ctx) -> {
    String rId = ctx.pathParam("reimbId");
    InputStream receipt = this.reimbursementService.getReceipt(rId);
    Tika tika = new Tika();
    String mimeType = tika.detect(receipt);
    ctx.header("Content-Type", mimeType);
    ctx.result(receipt);
  };

  private Handler resolveReimbursement = ctx -> {
    String jwt = ctx.header("Authorization").split(" ")[1];
    Jws<Claims> token = this.jwtService.parseJwt(jwt);

    if(!token.getBody().get("user_role").equals("finance manager")) {
      throw new UnauthorizedResponse("You are not a finance manager");
    }

    String rId = ctx.pathParam("reimbId");
    String status = ctx.queryParam("status");
    int uId = token.getBody().get("user_id", Integer.class);

    ResolveReimbursementDTO reimbursement = this.reimbursementService.resolveReimb(rId, status, uId);
    ctx.json(reimbursement);
  };

  @Override
  public void mapEndpoints(Javalin app) {
  app.get("/reimbursements", getallReimbursements);
  app.get("/users/{user_id}/reimbursements", getReimbursementsByUser);
  app.post("/users/{user_id}/reimbursements", addReimburement);
  app.get("reimbursements/{reimbId}/receipt", getReimbReceipt);
  app.patch("reimbursements/{reimbId}", resolveReimbursement);
  }
}
