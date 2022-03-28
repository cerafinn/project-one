package com.revature.dao;

import com.revature.dto.addReimbursementDTO;
import com.revature.model.Reimbursement;
import com.revature.utility.ConnectionUtility;

import java.sql.*;

public class ReimbursementDao {

  //add reimbursement
  public Reimbursement addReimburement(int employee, addReimbursementDTO dto) throws SQLException {
    try (Connection con = ConnectionUtility.getConnection()) {
      con.setAutoCommit(false);
      String sql = "UPDATE reimbursements SET "

    }
  }

  //view reimbursement -- all, by uid and by status?

  // get remit receipt

  //update reimbursement status
}
