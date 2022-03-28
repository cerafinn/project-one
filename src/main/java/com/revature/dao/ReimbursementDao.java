package com.revature.dao;

import com.revature.dto.addReimbursementDTO;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.utility.ConnectionUtility;

import java.sql.*;
import java.util.Calendar;

public class ReimbursementDao {

  //add reimbursement
  public Reimbursement addReimburement(int employeeId, addReimbursementDTO dto) throws SQLException {
    try (Connection con = ConnectionUtility.getConnection()) {
      con.setAutoCommit(false);
      String sql = "INSERT INTO reimbursement(reimb_amount, reimb_description, reimb_type, reimb_receipt, reimb_author) VALUES (?, ?, ?, ?, ?)";

      PreparedStatement ps = con.prepareStatement(sql);
      ps.setInt(1, dto.getRemitAmount());
      ps.setString(2, dto.getRemitDescription());
      ps.setInt(3, dto.getRemitType());
      ps.setBinaryStream(4, dto.getReceipt());
      ps.setInt(5, employeeId);

      ps.executeUpdate();
      ResultSet rs = ps.getGeneratedKeys();
      rs.next();
      int reimbId = rs.getInt(1);


      String sql2 = "SELECT * FROM users WHERE id = ?";
      PreparedStatement ps2 = con.prepareStatement(sql2);
      ps.setInt(1, employeeId);

      ResultSet rs2 = ps2.executeQuery();
      rs2.next();
      int sId = rs2.getInt("id");
      String employeeUsername = rs2.getString("username");
      String employeePassword = rs2.getString("password");
      String userRole = "employee";

      User employee = new User(sId, employeeUsername, employeePassword, userRole);
      Calendar cal = Calendar.getInstance();
      Timestamp currentDate = new Timestamp(cal.getTimeInMillis());

      Reimbursement reimb = new Reimbursement(reimbId, dto.getRemitAmount(), dto.getRemitDescription(), currentDate, null, employee, null, dto.getRemitType(), 1);
      con.commit();
      return reimb;
    }
  }

  //view reimbursement -- all, by uid and by status?

  // get remit receipt

  //update reimbursement status
}
