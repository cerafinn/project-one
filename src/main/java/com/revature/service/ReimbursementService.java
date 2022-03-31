package com.revature.service;

import com.revature.dao.ReimbursementDao;
import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.ResolveReimbursementDTO;
import com.revature.exception.ImageNotFoundException;
import com.revature.exception.InvalidImageException;
import com.revature.model.Reimbursement;
import org.apache.tika.Tika;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementService {
  private ReimbursementDao reimbursementDao;

  public ReimbursementService() { this.reimbursementDao = new ReimbursementDao(); }
  public ReimbursementService(ReimbursementDao mockDao) { this.reimbursementDao = mockDao; }

  //get all reimb
  public List<ResolveReimbursementDTO> getAllReimbursements() throws SQLException {
    List<Reimbursement> reimbursements = this.reimbursementDao.getAllReimb();
    List<ResolveReimbursementDTO> reimbDTOs = new ArrayList<>();

    for(Reimbursement reimbursement : reimbursements) {
      reimbDTOs.add(new ResolveReimbursementDTO(reimbursement.getId(), reimbursement.getRemitAmount(), reimbursement.getRemitDrescription(),
          reimbursement.getRemitSubmitted(), reimbursement.getRemitResolved(), reimbursement.getType(), reimbursement.getStatus(),
          reimbursement.getEmployee().getUsername(), reimbursement.getEmployee().getUserFirstName(), reimbursement.getEmployee().getUserLastName(),
          reimbursement.getManager().getUsername(), reimbursement.getManager().getUserFirstName(), reimbursement.getManager().getUserLastName()));
    }
    return reimbDTOs;
  }

  //get all by uid
  public List<ResolveReimbursementDTO> getReimbByUser(int userId) throws SQLException {
    List<Reimbursement> reimbursements = this.reimbursementDao.getAllReimbByUser(userId);
    List<ResolveReimbursementDTO> reimbDTOs = new ArrayList<>();

    for(Reimbursement reimbursement : reimbursements) {
      reimbDTOs.add(new ResolveReimbursementDTO(reimbursement.getId(), reimbursement.getRemitAmount(), reimbursement.getRemitDrescription(),
          reimbursement.getRemitSubmitted(), reimbursement.getRemitResolved(), reimbursement.getType(), reimbursement.getStatus(),
          reimbursement.getEmployee().getUsername(), reimbursement.getEmployee().getUserFirstName(), reimbursement.getEmployee().getUserLastName(),
          reimbursement.getManager().getUsername(), reimbursement.getManager().getUserFirstName(), reimbursement.getManager().getUserLastName()));
    }
    return reimbDTOs;
  }

  //add new reimb
  public ResolveReimbursementDTO addNewReimb(int employeeId, AddReimbursementDTO dto) throws SQLException, InvalidImageException, IOException {
    Tika tika = new Tika();
    String mimeType = tika.detect(dto.getReceipt());

    if(!mimeType.equals("image/jpg") && !mimeType.equals("image/jpeg") && !mimeType.equals("image/png") && !mimeType.equals("image/gif")) {
      throw new InvalidImageException("Image must be one of: JPEG, PNG or GIF");
    }
    Reimbursement addedReimb = this.reimbursementDao.addReimbursement(employeeId, dto);
    return new ResolveReimbursementDTO(addedReimb.getId(), addedReimb.getRemitAmount(), addedReimb.getRemitDrescription(),
        addedReimb.getRemitSubmitted(), addedReimb.getRemitResolved(), addedReimb.getType(), addedReimb.getStatus(), addedReimb.getEmployee().getUsername(),
        addedReimb.getEmployee().getUserFirstName(), addedReimb.getEmployee().getUserLastName(), null, null, null);
  }

  //update (resolve) reimb
  public ResolveReimbursementDTO resolveReimb(String reimbId, String status, int resolverId) throws SQLException {
    try {
      int intReimbId = Integer.parseInt(reimbId);
      int intStatus = Integer.parseInt(status);

      Reimbursement reimb = this.reimbursementDao.updateReimbStatus(intReimbId, intStatus, resolverId);
      return new ResolveReimbursementDTO(reimb.getId(), reimb.getRemitAmount(), reimb.getRemitDrescription(), reimb.getRemitSubmitted(),
          reimb.getRemitResolved(), reimb.getType(), reimb.getStatus(), reimb.getEmployee().getUsername(), reimb.getEmployee().getUserFirstName(),
          reimb.getEmployee().getUserLastName(), reimb.getManager().getUsername(), reimb.getManager().getUserFirstName(), reimb.getManager().getUserLastName());
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Reimbursement id must by a numerical value");
    }
  }

  //get receipt -- will these stay as bytea in sql, or try cloud services?
  public InputStream getReceipt(String reimbId) throws SQLException, ImageNotFoundException {
    try {
      int rId = Integer.parseInt(reimbId);
      InputStream receipt = this.reimbursementDao.getReimbReceipt(rId);
      if(receipt == null) {
        throw new ImageNotFoundException("Reimbursement " + reimbId + " does not have a receipt attached");
      }
      return receipt;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid reimbursement or employee id");
    }
  }

  public List<ResolveReimbursementDTO> getAllReimbursementsByStatus(String status) throws SQLException {
    int intStatus = Integer.parseInt(status);

    List<Reimbursement> reimbursements = this.reimbursementDao.getAllReimbByStatus(intStatus);
    List<ResolveReimbursementDTO> reimbDTOs = new ArrayList<>();

    for(Reimbursement reimbursement : reimbursements) {
      reimbDTOs.add(new ResolveReimbursementDTO(reimbursement.getId(), reimbursement.getRemitAmount(), reimbursement.getRemitDrescription(),
          reimbursement.getRemitSubmitted(), reimbursement.getRemitResolved(), reimbursement.getType(), reimbursement.getStatus(),
          reimbursement.getEmployee().getUsername(), reimbursement.getEmployee().getUserFirstName(), reimbursement.getEmployee().getUserLastName(),
          reimbursement.getManager().getUsername(), reimbursement.getManager().getUserFirstName(), reimbursement.getManager().getUserLastName()));
    }
    return reimbDTOs;
  }

  public List<ResolveReimbursementDTO> getReimbByUserAndStatus(int userId, String status) throws SQLException {
    int intStatus = Integer.parseInt(status);
    List<Reimbursement> reimbursements = this.reimbursementDao.getAllReimbByUserAndStatus(userId, intStatus);
    List<ResolveReimbursementDTO> reimbDTOs = new ArrayList<>();

    for(Reimbursement reimbursement : reimbursements) {
      reimbDTOs.add(new ResolveReimbursementDTO(reimbursement.getId(), reimbursement.getRemitAmount(), reimbursement.getRemitDrescription(),
          reimbursement.getRemitSubmitted(), reimbursement.getRemitResolved(), reimbursement.getType(), reimbursement.getStatus(),
          reimbursement.getEmployee().getUsername(), reimbursement.getEmployee().getUserFirstName(), reimbursement.getEmployee().getUserLastName(),
          reimbursement.getManager().getUsername(), reimbursement.getManager().getUserFirstName(), reimbursement.getManager().getUserLastName()));
    }
    return reimbDTOs;

  }
}
