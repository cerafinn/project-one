package com.revature.dao;

import com.revature.dto.addReimbursementDTO;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.utility.ConnectionUtility;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
      int userId = rs2.getInt("id");
      String username = rs.getString("username");
      String password = rs.getString("password");
      String userFirstName = rs.getString("user_first_name");
      String userLastName = rs.getString("user_last_name");
      String userEmail = rs.getString("user_email");
      String userRole = "employee";

      User employee = new User(userId, username, password, userFirstName, userLastName, userEmail, userRole);
      Calendar cal = Calendar.getInstance();
      Timestamp currentDate = new Timestamp(cal.getTimeInMillis());

      Reimbursement reimb = new Reimbursement(reimbId, dto.getRemitAmount(), dto.getRemitDescription(), currentDate, null, employee, null, dto.getRemitType(), 1);
      con.commit();
      return reimb;
    }
  }

  //view reimbursement -- all, by uid
  public List<Reimbursement> getAllReimb() throws SQLException {
    try (Connection con = ConnectionUtility.getConnection()) {
      List<Reimbursement> reimbursements = new ArrayList<>();
      String sql = "SELECT reimbursement.id AS reimb_id, reimb_amount AS amount, reimb_description AS description, reimbursement_type.type AS type, reimbursement_status.status AS status, " +
          "CONCAT(reimb_author.user_first_name, ' ', reimb_author.user_last_name) AS employee,  CONCAT(reimb_resolver.user_first_name, ' ', reimb_resolver.user_last_name) AS manager, " +
          "reimb_submitted AS submitted, reimb_resolved AS resolved FROM reimbursement LEFT JOIN reimbursement_type ON reimb_type_id = reimbursement_type.id LEFT JOIN reimbursement_status " +
          "ON reimb_status_id = reimbursement_status.id LEFT JOIN users reimb_author ON reimb_author.id = reimb_author LEFT JOIN users reimb_resolver ON reimb_resolver.id = reimb_resolver";

      PreparedStatement ps = con.prepareStatement(sql);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        int reimbId = rs.getInt("reimb_id");
        int reimbAmount = rs.getInt("reimb_amount");
        String reimbDescription = rs.getString("reimb_description");
        Timestamp reimbSubDate = rs.getTimestamp("reimb_submitted");
        Timestamp reimbResolvedDate = rs.getTimestamp("reimb_resolved");
        int type = rs.getInt("type");
        int status = rs.getInt("status");

        int eId = rs.getInt("reimb_author");
        String eUsername = rs.getString("username");
        String ePassword = rs.getString("password");
        String eUserFirstName = rs.getString("user_first_name");
        String eUserLastName = rs.getString("user_last_name");
        String eUserEmail = rs.getString("user_email");
        String eUserRole = "employee";

        User employee = new User(eId, eUsername, ePassword, eUserFirstName, eUserLastName, eUserEmail, eUserRole);

        int mId = rs.getInt("reimb_resolver");
        String mUsername = rs.getString("username");
        String mPassword = rs.getString("password");
        String mUserFirstName = rs.getString("user_first_name");
        String mUserLastName = rs.getString("user_last_name");
        String mUserEmail = rs.getString("user_email");
        String mUserRole = "finance manager";

        User manager = new User(mId, mUsername, mPassword, mUserFirstName, mUserLastName, mUserEmail, mUserRole);

        Reimbursement r = new Reimbursement(reimbId, reimbAmount, reimbDescription, reimbSubDate, reimbResolvedDate, employee, manager, type, status);
        reimbursements.add(r);
      }
      return reimbursements;
    }
  }

  public List<Reimbursement> getAllReimbByUser(int userId) throws SQLException {
    try (Connection con = ConnectionUtility.getConnection()) {
      List<Reimbursement> reimbursements = new ArrayList<>();
      String sql = "SELECT reimbursement.id AS id, reimb_amount AS amount, reimb_description AS description, reimbursement_type.type AS type, reimbursement_status.status AS status, " +
          "CONCAT(reimb_author.user_first_name, ' ', reimb_author.user_last_name) AS employee,  CONCAT(reimb_resolver.user_first_name, ' ', reimb_resolver.user_last_name) AS manager, " +
          "reimb_submitted AS submitted, reimb_resolved AS resolved FROM reimbursement LEFT JOIN reimbursement_type ON reimb_type_id = reimbursement_type.id LEFT JOIN reimbursement_status " +
          "ON reimb_status_id = reimbursement_status.id LEFT JOIN users reimb_author ON reimb_author.id = reimb_author LEFT JOIN users reimb_resolver ON reimb_resolver.id = reimb_resolver" +
          "WHERE reimb_author = ?";

      PreparedStatement ps = con.prepareStatement(sql);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        int reimbId = rs.getInt("reimb_id");
        int reimbAmount = rs.getInt("reimb_amount");
        String reimbDescription = rs.getString("reimb_description");
        Timestamp reimbSubDate = rs.getTimestamp("reimb_submitted");
        Timestamp reimbResolvedDate = rs.getTimestamp("reimb_resolved");
        int type = rs.getInt("type");
        int status = rs.getInt("status");

        int eId = rs.getInt("reimb_author");
        String eUsername = rs.getString("username");
        String ePassword = rs.getString("password");
        String eUserFirstName = rs.getString("user_first_name");
        String eUserLastName = rs.getString("user_last_name");
        String eUserEmail = rs.getString("user_email");
        String eUserRole = "employee";

        User employee = new User(eId, eUsername, ePassword, eUserFirstName, eUserLastName, eUserEmail, eUserRole);

        int mId = rs.getInt("reimb_resolver");
        String mUsername = rs.getString("username");
        String mPassword = rs.getString("password");
        String mUserFirstName = rs.getString("user_first_name");
        String mUserLastName = rs.getString("user_last_name");
        String mUserEmail = rs.getString("user_email");
        String mUserRole = "finance manager";

        User manager = new User(mId, mUsername, mPassword, mUserFirstName, mUserLastName, mUserEmail, mUserRole);

        Reimbursement r = new Reimbursement(reimbId, reimbAmount, reimbDescription, reimbSubDate, reimbResolvedDate, employee, manager, type, status);
        reimbursements.add(r);
      }
      return reimbursements;
    }
  }

  // get remit receipt
  public InputStream getReimbReceipt(int reimbId) throws SQLException {
    try (Connection con = ConnectionUtility.getConnection()) {
      String sql = "SELECT reimb_receipt FROM reimbursement WHERE id = ?";

      PreparedStatement ps = con.prepareStatement(sql);
      ps.setInt(1, reimbId);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        InputStream receipt = rs.getBinaryStream("reimb_receipt");
        return receipt;
      } else {
        return null;
      }
    }
  }

  //update reimbursement status
  public Reimbursement updateReimbStatus(int reimbId, int status, int managerId) throws SQLException {
    try (Connection con = ConnectionUtility.getConnection()) {
      con.setAutoCommit(false);
      String sql = "UPDATE reimbursement SET status = ?, reimb_resolver = ? WHERE id = ?";

      PreparedStatement ps = con.prepareStatement(sql);
      ps.setInt(1, status);
      ps.setInt(2, managerId);
      ps.setInt(3, reimbId);

      ps.executeUpdate();

      String sql2 = "SELECT reimbursement.id AS id, reimb_amount AS amount, reimb_description AS description, reimbursement_type.type AS type, reimbursement_status.status AS status, " +
          "CONCAT(reimb_author.user_first_name, ' ', reimb_author.user_last_name) AS employee,  CONCAT(reimb_resolver.user_first_name, ' ', reimb_resolver.user_last_name) AS manager, " +
          "reimb_submitted AS submitted, reimb_resolved AS resolved FROM reimbursement LEFT JOIN reimbursement_type ON reimb_type_id = reimbursement_type.id LEFT JOIN reimbursement_status " +
          "ON reimb_status_id = reimbursement_status.id LEFT JOIN users reimb_author ON reimb_author.id = reimb_author LEFT JOIN users reimb_resolver ON reimb_resolver.id = reimb_resolver" +
          "WHERE id = ?";

      PreparedStatement ps2 = con.prepareStatement(sql2);
      ps2.setInt(1, reimbId);
      ResultSet rs2 = ps.executeQuery();

      rs2.next();
      int rId = rs2.getInt("reimb_id");
      int reimbAmount = rs2.getInt("reimb_amount");
      String reimbDescription = rs2.getString("reimb_description");
      Timestamp reimbSubDate = rs2.getTimestamp("reimb_submitted");
      Timestamp reimbResolvedDate = rs2.getTimestamp("reimb_resolved");
      int type = rs2.getInt("type");
      int rStatus = rs2.getInt("status");

      int eId = rs2.getInt("reimb_author");
      String eUsername = rs2.getString("username");
      String ePassword = rs2.getString("password");
      String eUserFirstName = rs2.getString("user_first_name");
      String eUserLastName = rs2.getString("user_last_name");
      String eUserEmail = rs2.getString("user_email");
      String eUserRole = "employee";

      User employee = new User(eId, eUsername, ePassword, eUserFirstName, eUserLastName, eUserEmail, eUserRole);

      int mId = rs2.getInt("reimb_resolver");
      String mUsername = rs2.getString("username");
      String mPassword = rs2.getString("password");
      String mUserFirstName = rs2.getString("user_first_name");
      String mUserLastName = rs2.getString("user_last_name");
      String mUserEmail = rs2.getString("user_email");
      String mUserRole = "finance manager";

      User manager = new User(mId, mUsername, mPassword, mUserFirstName, mUserLastName, mUserEmail, mUserRole);

      Reimbursement r = new Reimbursement(rId, reimbAmount, reimbDescription, reimbSubDate, reimbResolvedDate, employee, manager, type, rStatus);

      con.commit();
      return r;
    }
  }
}
