package com.revature.service;

import com.revature.dao.ReimbursementDao;
import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.ResolveReimbursementDTO;
import com.revature.model.Reimbursement;
import org.apache.tika.Tika;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ReimbursementService {
  private ReimbursementDao reimbursementDao;

  public ReimbursementService() { this.reimbursementDao = new ReimbursementDao(); }
  public ReimbursementService(ReimbursementDao mockDao) { this.reimbursementDao = mockDao; }

  //get all reimb
  public List<ResolveReimbursementDTO> getAllReimbursements() throws SQLException {
    List<Reimbursement> reimbursements = this.reimbursementDao.getAllReimb();
    return reimbursements;
  }

  //get all by uid
  public List<ResolveReimbursementDTO> getReimbByUser(int userId) throws SQLException {
    List<Reimbursement> reimbursements = this.reimbursementDao.getAllReimbByUser(userId);
    return reimbursements;
  }

  //add new reimb
  public ResolveReimbursementDTO addNewReimb(int employeeId, AddReimbursementDTO dto) throws SQLException, InvalidImageException, IOException {
    Tika tika = new Tika();
    String mimeType = tika.detect(dto.getReceipt());
  }

  //update (resolve) reimb
  public ResolveReimbursementDTO resolveReimb(String reimbId, String status, int resolverId) throws SQLException {
    try {
      int intReimbId = Integer.parseInt(reimbId);
      int intStatus = Integer.parseInt(status);

      Reimbursement reimb = this.reimbursementDao.updateReimbStatus(intReimbId, intStatus, resolverId);
      return new ResolveReimbursementDTO;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Reimbursement id must by a numerical value");
    }
  }

  //get receipt -- will these stay as bytea in sql, or try cloud services?
}
