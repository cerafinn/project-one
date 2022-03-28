package com.revature.service;

import com.revature.dao.ReimbursementDao;
import com.revature.model.Reimbursement;

import java.sql.SQLException;
import java.util.List;

public class ReimbursementService {
  private ReimbursementDao reimbursementDao;

  public ReimbursementService() { this.reimbursementDao = new ReimbursementDao(); }
  public ReimbursementService(ReimbursementDao mockDao) { this.reimbursementDao = mockDao; }

  //get all reimb
  public List<Reimbursement> getAllReimbursements() throws SQLException {
    List<Reimbursement> reimbursements = this.reimbursementDao.getAllReimb();
    return reimbursements;
  }

  //get all by uid
  public List<Reimbursement> getReimbByUser(int userId) throws SQLException {
    List<Reimbursement> reimbursements = this.reimbursementDao.getAllReimbByUser(userId);
    return reimbursements;
  }

  //add new reimb

  //update (resolve) reimb

  //get receipt -- will these stay as bytea in sql, or try cloud services?
}
