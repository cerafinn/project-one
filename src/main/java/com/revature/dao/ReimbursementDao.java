package com.revature.dao;

import com.revature.dto.AddReimbursementDTO;
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
  public Reimbursement addReimbursement(int employeeId, AddReimbursementDTO dto) throws SQLException {
    try (Connection con = ConnectionUtility.getConnection()) {
      con.setAutoCommit(false);
      String sql = "INSERT INTO reimbursement(reimb_amount, reimb_description, reimb_submitted, reimb_type_id, reimb_status_id, reimb_receipt, reimb_author) " +
          "VALUES (?, ?, ?, ?, ?, ?, ?)";

      try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        ps.setInt(1, dto.getRemitAmount());
        ps.setString(2, dto.getRemitDescription());
        //TODO fix date
        ps.setTimestamp(3, dto.getRemitSubmitted());
        ps.setInt(4, dto.getRemitType());
        ps.setInt(5, 1);
        ps.setBinaryStream(6, dto.getReceipt());
        ps.setInt(7, employeeId);

        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        int reimbId = rs.getInt(1);

//        String sql2 = "SELECT * FROM reimbursement WHERE id = ?";
        String sql2 = "SELECT * FROM users WHERE id = ?";

        try (PreparedStatement ps2 = con.prepareStatement(sql2)) {
          ps2.setInt(1, employeeId);
          ResultSet rs2 = ps2.executeQuery();
          rs2.next();
          int userId = rs2.getInt("id");
          String username = rs2.getString("username");
          String password = rs2.getString("password");
          String userFirstName = rs2.getString("user_first_name");
          String userLastName = rs2.getString("user_last_name");
          String userEmail = rs2.getString("user_email");
          String userRole = "employee";

          User employee = new User(userId, username, password, userFirstName, userLastName, userEmail, userRole);
//TODO fix date
          Calendar cal = Calendar.getInstance();
          Timestamp currentDate = new Timestamp(cal.getTimeInMillis());

          Reimbursement reimb = new Reimbursement(reimbId, dto.getRemitAmount(), dto.getRemitDescription(), currentDate, null, employee, null, dto.getRemitType(), 1);
          con.commit();
          return reimb;
        }
      }
    }
  }

  //view reimbursement -- all, by uid
  public List<Reimbursement> getAllReimb() throws SQLException {
    try (Connection con = ConnectionUtility.getConnection()) {
      List<Reimbursement> reimbursements = new ArrayList<>();
      String sql = "SELECT reimbursement.id AS reimb_id, reimb_amount AS amount, reimb_description AS description, reimbursement.reimb_type_id AS type, reimbursement.reimb_status_id AS status, " +
          "reimbursement.reimb_author AS reimb_author, reimb_author.user_first_name AS e_first_name, reimb_author.user_last_name AS e_last_name, reimb_author.username AS e_username, reimb_author.password AS e_password, reimb_author.user_email AS e_email, " +
          "reimbursement.reimb_resolver AS reimb_resolver, reimb_resolver.user_first_name AS m_first_name, reimb_resolver.user_last_name AS m_last_name, reimb_resolver.username AS m_username, reimb_resolver.password AS m_password, reimb_resolver.user_email AS m_email, " +
          "reimb_submitted AS submitted, reimb_resolved AS resolved FROM reimbursement " +
          "LEFT JOIN reimbursement_type ON reimb_type_id = reimbursement_type.id LEFT JOIN reimbursement_status ON reimb_status_id = reimbursement_status.id " +
          "LEFT JOIN users reimb_author ON reimb_author.id = reimb_author LEFT JOIN users reimb_resolver ON reimb_resolver.id = reimb_resolver";

      PreparedStatement ps = con.prepareStatement(sql);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        int reimbId = rs.getInt("reimb_id");
        int reimbAmount = rs.getInt("amount");
        String reimbDescription = rs.getString("description");


        // TODO fix date
        Timestamp reimbSubDate = rs.getTimestamp("submitted");
        Timestamp reimbResolvedDate = rs.getTimestamp("resolved");

        int type = rs.getInt("type");
        int status = rs.getInt("status");

        int eId = rs.getInt("reimb_author");
        String eUsername = rs.getString("e_username");
        String ePassword = rs.getString("e_password");
        String eUserFirstName = rs.getString("e_first_name");
        String eUserLastName = rs.getString("e_last_name");
        String eUserEmail = rs.getString("e_email");
        String eUserRole = "employee";

        User employee = new User(eId, eUsername, ePassword, eUserFirstName, eUserLastName, eUserEmail, eUserRole);

        int mId = rs.getInt("reimb_resolver");
        String mUsername = rs.getString("m_username");
        String mPassword = rs.getString("m_password");
        String mUserFirstName = rs.getString("m_first_name");
        String mUserLastName = rs.getString("m_last_name");
        String mUserEmail = rs.getString("m_email");
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
      String sql = "SELECT reimbursement.id AS reimb_id, reimb_amount AS amount, reimb_description AS description, reimbursement.reimb_type_id AS type, reimbursement.reimb_status_id AS status, " +
          "reimbursement.reimb_author AS reimb_author, reimb_author.user_first_name AS e_first_name, reimb_author.user_last_name AS e_last_name, reimb_author.username AS e_username, reimb_author.password AS e_password, reimb_author.user_email AS e_email, " +
          "reimbursement.reimb_resolver AS reimb_resolver, reimb_resolver.user_first_name AS m_first_name, reimb_resolver.user_last_name AS m_last_name, reimb_resolver.username AS m_username, reimb_resolver.password AS m_password, reimb_resolver.user_email AS m_email, " +
          "reimb_submitted AS submitted, reimb_resolved AS resolved FROM reimbursement " +
          "LEFT JOIN reimbursement_type ON reimb_type_id = reimbursement_type.id LEFT JOIN reimbursement_status ON reimb_status_id = reimbursement_status.id " +
          "LEFT JOIN users reimb_author ON reimb_author.id = reimb_author LEFT JOIN users reimb_resolver ON reimb_resolver.id = reimb_resolver " +
          "WHERE reimb_author = ?";

      PreparedStatement ps = con.prepareStatement(sql);
      ps.setInt(1, userId);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        int reimbId = rs.getInt("reimb_id");
        int reimbAmount = rs.getInt("amount");
        String reimbDescription = rs.getString("description");

        // todo fix date
        Timestamp reimbSubDate = rs.getTimestamp("submitted");
        Timestamp reimbResolvedDate = rs.getTimestamp("resolved");

        int type = rs.getInt("type");
        int status = rs.getInt("status");

        int eId = rs.getInt("reimb_author");
        String eUsername = rs.getString("e_username");
        String ePassword = rs.getString("e_password");
        String eUserFirstName = rs.getString("e_first_name");
        String eUserLastName = rs.getString("e_last_name");
        String eUserEmail = rs.getString("e_email");
        String eUserRole = "employee";

        User employee = new User(eId, eUsername, ePassword, eUserFirstName, eUserLastName, eUserEmail, eUserRole);

        int mId = rs.getInt("reimb_resolver");
        String mUsername = rs.getString("m_username");
        String mPassword = rs.getString("m_password");
        String mUserFirstName = rs.getString("m_first_name");
        String mUserLastName = rs.getString("m_last_name");
        String mUserEmail = rs.getString("m_email");
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
      String sql = "UPDATE reimbursement SET reimb_status_id = ?, reimb_resolver = ? WHERE id = ?";

      try(PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, status);
        ps.setInt(2, managerId);
        ps.setInt(3, reimbId);

        ps.executeUpdate();

        String sql2 = "SELECT reimbursement.id AS reimb_id, reimb_amount AS amount, reimb_description AS description, reimbursement.reimb_type_id AS type, reimbursement.reimb_status_id AS status, " +
            "reimbursement.reimb_author AS reimb_author, reimb_author.user_first_name AS e_first_name, reimb_author.user_last_name AS e_last_name, reimb_author.username AS e_username, reimb_author.password AS e_password, reimb_author.user_email AS e_email, " +
            "reimbursement.reimb_resolver AS reimb_resolver, reimb_resolver.user_first_name AS m_first_name, reimb_resolver.user_last_name AS m_last_name, reimb_resolver.username AS m_username, reimb_resolver.password AS m_password, reimb_resolver.user_email AS m_email, " +
            "reimb_submitted AS submitted, reimb_resolved AS resolved FROM reimbursement " +
            "LEFT JOIN reimbursement_type ON reimb_type_id = reimbursement_type.id LEFT JOIN reimbursement_status ON reimb_status_id = reimbursement_status.id " +
            "LEFT JOIN users reimb_author ON reimb_author.id = reimb_author LEFT JOIN users reimb_resolver ON reimb_resolver.id = reimb_resolver " +
            "WHERE reimbursement.id = ?";

        try (PreparedStatement ps2 = con.prepareStatement(sql2)) {
          ps2.setInt(1, reimbId);
          ResultSet rs2 = ps2.executeQuery();

          rs2.next();
          int rId = rs2.getInt("reimb_id");
          int reimbAmount = rs2.getInt("amount");
          String reimbDescription = rs2.getString("description");

          // TODO: fix time
          Timestamp reimbSubDate = rs2.getTimestamp("submitted");
          Calendar cal = Calendar.getInstance();
          Timestamp reimbResolvedDate = new Timestamp(cal.getTimeInMillis());

          int type = rs2.getInt("type");
          int rStatus = rs2.getInt("status");

          int eId = rs2.getInt("reimb_author");
          String eUsername = rs2.getString("e_username");
          String ePassword = rs2.getString("e_password");
          String eUserFirstName = rs2.getString("e_first_name");
          String eUserLastName = rs2.getString("e_last_name");
          String eUserEmail = rs2.getString("e_email");
          String eUserRole = "employee";

          User employee = new User(eId, eUsername, ePassword, eUserFirstName, eUserLastName, eUserEmail, eUserRole);

          int mId = rs2.getInt("reimb_resolver");
          String mUsername = rs2.getString("m_username");
          String mPassword = rs2.getString("m_password");
          String mUserFirstName = rs2.getString("m_first_name");
          String mUserLastName = rs2.getString("m_last_name");
          String mUserEmail = rs2.getString("m_email");
          String mUserRole = "finance manager";

          User manager = new User(mId, mUsername, mPassword, mUserFirstName, mUserLastName, mUserEmail, mUserRole);

          Reimbursement r = new Reimbursement(rId, reimbAmount, reimbDescription, reimbSubDate, reimbResolvedDate, employee, manager, type, rStatus);

          con.commit();
          return r;
        }
      }
    }
  }

  public List<Reimbursement> getAllReimbByStatus(int statusId) throws SQLException {
    try (Connection con = ConnectionUtility.getConnection()) {
      List<Reimbursement> reimbursements = new ArrayList<>();
      String sql = "SELECT reimbursement.id AS reimb_id, reimb_amount AS amount, reimb_description AS description, reimbursement.reimb_type_id AS type, reimbursement.reimb_status_id AS status, " +
          "reimbursement.reimb_author AS reimb_author, reimb_author.user_first_name AS e_first_name, reimb_author.user_last_name AS e_last_name, reimb_author.username AS e_username, reimb_author.password AS e_password, reimb_author.user_email AS e_email, " +
          "reimbursement.reimb_resolver AS reimb_resolver, reimb_resolver.user_first_name AS m_first_name, reimb_resolver.user_last_name AS m_last_name, reimb_resolver.username AS m_username, reimb_resolver.password AS m_password, reimb_resolver.user_email AS m_email, " +
          "reimb_submitted AS submitted, reimb_resolved AS resolved FROM reimbursement " +
          "LEFT JOIN reimbursement_type ON reimb_type_id = reimbursement_type.id LEFT JOIN reimbursement_status ON reimb_status_id = reimbursement_status.id " +
          "LEFT JOIN users reimb_author ON reimb_author.id = reimb_author LEFT JOIN users reimb_resolver ON reimb_resolver.id = reimb_resolver " +
          "WHERE reimb_status_id = ?";

      PreparedStatement ps = con.prepareStatement(sql);
      ps.setInt(1, statusId);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        int reimbId = rs.getInt("reimb_id");
        int reimbAmount = rs.getInt("amount");
        String reimbDescription = rs.getString("description");

        // TODO fix date
        Timestamp reimbSubDate = rs.getTimestamp("submitted");
        Timestamp reimbResolvedDate = rs.getTimestamp("resolved");

        int type = rs.getInt("type");
        int status = rs.getInt("status");

        int eId = rs.getInt("reimb_author");
        String eUsername = rs.getString("e_username");
        String ePassword = rs.getString("e_password");
        String eUserFirstName = rs.getString("e_first_name");
        String eUserLastName = rs.getString("e_last_name");
        String eUserEmail = rs.getString("e_email");
        String eUserRole = "employee";

        User employee = new User(eId, eUsername, ePassword, eUserFirstName, eUserLastName, eUserEmail, eUserRole);

        int mId = rs.getInt("reimb_resolver");
        String mUsername = rs.getString("m_username");
        String mPassword = rs.getString("m_password");
        String mUserFirstName = rs.getString("m_first_name");
        String mUserLastName = rs.getString("m_last_name");
        String mUserEmail = rs.getString("m_email");
        String mUserRole = "finance manager";

        User manager = new User(mId, mUsername, mPassword, mUserFirstName, mUserLastName, mUserEmail, mUserRole);

        Reimbursement r = new Reimbursement(reimbId, reimbAmount, reimbDescription, reimbSubDate, reimbResolvedDate, employee, manager, type, status);
        reimbursements.add(r);
      }
      return reimbursements;
    }
  }

  public List<Reimbursement> getAllReimbByUserAndStatus(int userId, int statusId) throws SQLException {
    try (Connection con = ConnectionUtility.getConnection()) {
      List<Reimbursement> reimbursements = new ArrayList<>();
      String sql = "SELECT reimbursement.id AS reimb_id, reimb_amount AS amount, reimb_description AS description, reimbursement.reimb_type_id AS type, reimbursement.reimb_status_id AS status, " +
          "reimbursement.reimb_author AS reimb_author, reimb_author.user_first_name AS e_first_name, reimb_author.user_last_name AS e_last_name, reimb_author.username AS e_username, reimb_author.password AS e_password, reimb_author.user_email AS e_email, " +
          "reimbursement.reimb_resolver AS reimb_resolver, reimb_resolver.user_first_name AS m_first_name, reimb_resolver.user_last_name AS m_last_name, reimb_resolver.username AS m_username, reimb_resolver.password AS m_password, reimb_resolver.user_email AS m_email, " +
          "reimb_submitted AS submitted, reimb_resolved AS resolved FROM reimbursement " +
          "LEFT JOIN reimbursement_type ON reimb_type_id = reimbursement_type.id LEFT JOIN reimbursement_status ON reimb_status_id = reimbursement_status.id " +
          "LEFT JOIN users reimb_author ON reimb_author.id = reimb_author LEFT JOIN users reimb_resolver ON reimb_resolver.id = reimb_resolver " +
          "WHERE reimb_author = ? AND reimb_status_id = ?";

      PreparedStatement ps = con.prepareStatement(sql);
      ps.setInt(1, userId);
      ps.setInt(2, statusId);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        int reimbId = rs.getInt("reimb_id");
        int reimbAmount = rs.getInt("amount");
        String reimbDescription = rs.getString("description");

        // todo fix date
        Timestamp reimbSubDate = rs.getTimestamp("submitted");
        Timestamp reimbResolvedDate = rs.getTimestamp("resolved");

        int type = rs.getInt("type");
        int status = rs.getInt("status");

        int eId = rs.getInt("reimb_author");
        String eUsername = rs.getString("e_username");
        String ePassword = rs.getString("e_password");
        String eUserFirstName = rs.getString("e_first_name");
        String eUserLastName = rs.getString("e_last_name");
        String eUserEmail = rs.getString("e_email");
        String eUserRole = "employee";

        User employee = new User(eId, eUsername, ePassword, eUserFirstName, eUserLastName, eUserEmail, eUserRole);

        int mId = rs.getInt("reimb_resolver");
        String mUsername = rs.getString("m_username");
        String mPassword = rs.getString("m_password");
        String mUserFirstName = rs.getString("m_first_name");
        String mUserLastName = rs.getString("m_last_name");
        String mUserEmail = rs.getString("m_email");
        String mUserRole = "finance manager";

        User manager = new User(mId, mUsername, mPassword, mUserFirstName, mUserLastName, mUserEmail, mUserRole);

        Reimbursement r = new Reimbursement(reimbId, reimbAmount, reimbDescription, reimbSubDate, reimbResolvedDate, employee, manager, type, status);
        reimbursements.add(r);
      }
      return reimbursements;
    }
  }

  public List<Reimbursement> getAllReimbByStatusAndType(int statusId, int typeId) throws SQLException {
    try (Connection con = ConnectionUtility.getConnection()) {
      List<Reimbursement> reimbursements = new ArrayList<>();
      String sql = "SELECT reimbursement.id AS reimb_id, reimb_amount AS amount, reimb_description AS description, reimbursement.reimb_type_id AS type, reimbursement.reimb_status_id AS status, " +
          "reimbursement.reimb_author AS reimb_author, reimb_author.user_first_name AS e_first_name, reimb_author.user_last_name AS e_last_name, reimb_author.username AS e_username, reimb_author.password AS e_password, reimb_author.user_email AS e_email, " +
          "reimbursement.reimb_resolver AS reimb_resolver, reimb_resolver.user_first_name AS m_first_name, reimb_resolver.user_last_name AS m_last_name, reimb_resolver.username AS m_username, reimb_resolver.password AS m_password, reimb_resolver.user_email AS m_email, " +
          "reimb_submitted AS submitted, reimb_resolved AS resolved FROM reimbursement " +
          "LEFT JOIN reimbursement_type ON reimb_type_id = reimbursement_type.id LEFT JOIN reimbursement_status ON reimb_status_id = reimbursement_status.id " +
          "LEFT JOIN users reimb_author ON reimb_author.id = reimb_author LEFT JOIN users reimb_resolver ON reimb_resolver.id = reimb_resolver " +
          "WHERE reimb_status_id = ? AND reimb_type_id = ?";

      PreparedStatement ps = con.prepareStatement(sql);
      ps.setInt(1, statusId);
      ps.setInt(2, typeId);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        int reimbId = rs.getInt("reimb_id");
        int reimbAmount = rs.getInt("amount");
        String reimbDescription = rs.getString("description");

        Timestamp timestamp = rs.getTimestamp("resolved");
        String reimbSubDate;
        if(timestamp != null) {
          Date date = new Date(rs.getTimestamp("resolved").getTime());
          reimbSubDate = date.toString();
        } else {
          reimbSubDate = null;
        }
        Timestamp timestamp2 = rs.getTimestamp("resolved");
        String reimbResolvedDate;
        if(timestamp2 != null) {
          Date date2 = new Date(rs.getTimestamp("resolved").getTime());
          reimbResolvedDate = date2.toString();
        } else {
          reimbResolvedDate = null;
        }

        int type = rs.getInt("type");
        int status = rs.getInt("status");

        int eId = rs.getInt("reimb_author");
        String eUsername = rs.getString("e_username");
        String ePassword = rs.getString("e_password");
        String eUserFirstName = rs.getString("e_first_name");
        String eUserLastName = rs.getString("e_last_name");
        String eUserEmail = rs.getString("e_email");
        String eUserRole = "employee";

        User employee = new User(eId, eUsername, ePassword, eUserFirstName, eUserLastName, eUserEmail, eUserRole);

        int mId = rs.getInt("reimb_resolver");
        String mUsername = rs.getString("m_username");
        String mPassword = rs.getString("m_password");
        String mUserFirstName = rs.getString("m_first_name");
        String mUserLastName = rs.getString("m_last_name");
        String mUserEmail = rs.getString("m_email");
        String mUserRole = "finance manager";

        User manager = new User(mId, mUsername, mPassword, mUserFirstName, mUserLastName, mUserEmail, mUserRole);

        Reimbursement r = new Reimbursement(reimbId, reimbAmount, reimbDescription, reimbSubDate, reimbResolvedDate, employee, manager, type, status);
        reimbursements.add(r);
      }
      return reimbursements;
    }
  }

  public List<Reimbursement> getAllReimbByUserStatusAndType(int userId, int statusId, int typeId) throws SQLException {
    try (Connection con = ConnectionUtility.getConnection()) {
      List<Reimbursement> reimbursements = new ArrayList<>();
      String sql = "SELECT reimbursement.id AS reimb_id, reimb_amount AS amount, reimb_description AS description, reimbursement.reimb_type_id AS type, reimbursement.reimb_status_id AS status, " +
          "reimbursement.reimb_author AS reimb_author, reimb_author.user_first_name AS e_first_name, reimb_author.user_last_name AS e_last_name, reimb_author.username AS e_username, reimb_author.password AS e_password, reimb_author.user_email AS e_email, " +
          "reimbursement.reimb_resolver AS reimb_resolver, reimb_resolver.user_first_name AS m_first_name, reimb_resolver.user_last_name AS m_last_name, reimb_resolver.username AS m_username, reimb_resolver.password AS m_password, reimb_resolver.user_email AS m_email, " +
          "reimb_submitted AS submitted, reimb_resolved AS resolved FROM reimbursement " +
          "LEFT JOIN reimbursement_type ON reimb_type_id = reimbursement_type.id LEFT JOIN reimbursement_status ON reimb_status_id = reimbursement_status.id " +
          "LEFT JOIN users reimb_author ON reimb_author.id = reimb_author LEFT JOIN users reimb_resolver ON reimb_resolver.id = reimb_resolver " +
          "WHERE reimb_author = ? AND reimb_status_id = ? AND reimb_type_id = ?";

      PreparedStatement ps = con.prepareStatement(sql);
      ps.setInt(1, userId);
      ps.setInt(2, statusId);
      ps.setInt(3, typeId);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        int reimbId = rs.getInt("reimb_id");
        int reimbAmount = rs.getInt("amount");
        String reimbDescription = rs.getString("description");

        Timestamp timestamp = rs.getTimestamp("resolved");
        String reimbSubDate;
        if(timestamp != null) {
          Date date = new Date(rs.getTimestamp("resolved").getTime());
          reimbSubDate = date.toString();
        } else {
          reimbSubDate = null;
        }
        Timestamp timestamp2 = rs.getTimestamp("resolved");
        String reimbResolvedDate;
        if(timestamp2 != null) {
          Date date2 = new Date(rs.getTimestamp("resolved").getTime());
          reimbResolvedDate = date2.toString();
        } else {
          reimbResolvedDate = null;
        }

        int type = rs.getInt("type");
        int status = rs.getInt("status");

        int eId = rs.getInt("reimb_author");
        String eUsername = rs.getString("e_username");
        String ePassword = rs.getString("e_password");
        String eUserFirstName = rs.getString("e_first_name");
        String eUserLastName = rs.getString("e_last_name");
        String eUserEmail = rs.getString("e_email");
        String eUserRole = "employee";

        User employee = new User(eId, eUsername, ePassword, eUserFirstName, eUserLastName, eUserEmail, eUserRole);

        int mId = rs.getInt("reimb_resolver");
        String mUsername = rs.getString("m_username");
        String mPassword = rs.getString("m_password");
        String mUserFirstName = rs.getString("m_first_name");
        String mUserLastName = rs.getString("m_last_name");
        String mUserEmail = rs.getString("m_email");
        String mUserRole = "finance manager";

        User manager = new User(mId, mUsername, mPassword, mUserFirstName, mUserLastName, mUserEmail, mUserRole);

        Reimbursement r = new Reimbursement(reimbId, reimbAmount, reimbDescription, reimbSubDate, reimbResolvedDate, employee, manager, type, status);
        reimbursements.add(r);
      }
      return reimbursements;
    }
  }

  public List<Reimbursement> getAllReimbByType(int typeId) throws SQLException {
    try (Connection con = ConnectionUtility.getConnection()) {
      List<Reimbursement> reimbursements = new ArrayList<>();
      String sql = "SELECT reimbursement.id AS reimb_id, reimb_amount AS amount, reimb_description AS description, reimbursement.reimb_type_id AS type, reimbursement.reimb_status_id AS status, " +
          "reimbursement.reimb_author AS reimb_author, reimb_author.user_first_name AS e_first_name, reimb_author.user_last_name AS e_last_name, reimb_author.username AS e_username, reimb_author.password AS e_password, reimb_author.user_email AS e_email, " +
          "reimbursement.reimb_resolver AS reimb_resolver, reimb_resolver.user_first_name AS m_first_name, reimb_resolver.user_last_name AS m_last_name, reimb_resolver.username AS m_username, reimb_resolver.password AS m_password, reimb_resolver.user_email AS m_email, " +
          "reimb_submitted AS submitted, reimb_resolved AS resolved FROM reimbursement " +
          "LEFT JOIN reimbursement_type ON reimb_type_id = reimbursement_type.id LEFT JOIN reimbursement_status ON reimb_status_id = reimbursement_status.id " +
          "LEFT JOIN users reimb_author ON reimb_author.id = reimb_author LEFT JOIN users reimb_resolver ON reimb_resolver.id = reimb_resolver " +
          "WHERE reimb_type_id = ?";

      PreparedStatement ps = con.prepareStatement(sql);
      ps.setInt(1, typeId);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        int reimbId = rs.getInt("reimb_id");
        int reimbAmount = rs.getInt("amount");
        String reimbDescription = rs.getString("description");

        Timestamp timestamp = rs.getTimestamp("resolved");
        String reimbSubDate;
        if(timestamp != null) {
          Date date = new Date(rs.getTimestamp("resolved").getTime());
          reimbSubDate = date.toString();
        } else {
          reimbSubDate = null;
        }
        Timestamp timestamp2 = rs.getTimestamp("resolved");
        String reimbResolvedDate;
        if(timestamp2 != null) {
          Date date2 = new Date(rs.getTimestamp("resolved").getTime());
          reimbResolvedDate = date2.toString();
        } else {
          reimbResolvedDate = null;
        }

        int type = rs.getInt("type");
        int status = rs.getInt("status");

        int eId = rs.getInt("reimb_author");
        String eUsername = rs.getString("e_username");
        String ePassword = rs.getString("e_password");
        String eUserFirstName = rs.getString("e_first_name");
        String eUserLastName = rs.getString("e_last_name");
        String eUserEmail = rs.getString("e_email");
        String eUserRole = "employee";

        User employee = new User(eId, eUsername, ePassword, eUserFirstName, eUserLastName, eUserEmail, eUserRole);

        int mId = rs.getInt("reimb_resolver");
        String mUsername = rs.getString("m_username");
        String mPassword = rs.getString("m_password");
        String mUserFirstName = rs.getString("m_first_name");
        String mUserLastName = rs.getString("m_last_name");
        String mUserEmail = rs.getString("m_email");
        String mUserRole = "finance manager";

        User manager = new User(mId, mUsername, mPassword, mUserFirstName, mUserLastName, mUserEmail, mUserRole);

        Reimbursement r = new Reimbursement(reimbId, reimbAmount, reimbDescription, reimbSubDate, reimbResolvedDate, employee, manager, type, status);
        reimbursements.add(r);
      }
      return reimbursements;
    }
  }

  public List<Reimbursement> getAllReimbByUserAndType(int userId, int typeId) throws SQLException {
    try (Connection con = ConnectionUtility.getConnection()) {
      List<Reimbursement> reimbursements = new ArrayList<>();
      String sql = "SELECT reimbursement.id AS reimb_id, reimb_amount AS amount, reimb_description AS description, reimbursement.reimb_type_id AS type, reimbursement.reimb_status_id AS status, " +
          "reimbursement.reimb_author AS reimb_author, reimb_author.user_first_name AS e_first_name, reimb_author.user_last_name AS e_last_name, reimb_author.username AS e_username, reimb_author.password AS e_password, reimb_author.user_email AS e_email, " +
          "reimbursement.reimb_resolver AS reimb_resolver, reimb_resolver.user_first_name AS m_first_name, reimb_resolver.user_last_name AS m_last_name, reimb_resolver.username AS m_username, reimb_resolver.password AS m_password, reimb_resolver.user_email AS m_email, " +
          "reimb_submitted AS submitted, reimb_resolved AS resolved FROM reimbursement " +
          "LEFT JOIN reimbursement_type ON reimb_type_id = reimbursement_type.id LEFT JOIN reimbursement_status ON reimb_status_id = reimbursement_status.id " +
          "LEFT JOIN users reimb_author ON reimb_author.id = reimb_author LEFT JOIN users reimb_resolver ON reimb_resolver.id = reimb_resolver " +
          "WHERE reimb_author = ? AND reimb_type_id = ?";

      PreparedStatement ps = con.prepareStatement(sql);
      ps.setInt(1, userId);
      ps.setInt(2, typeId);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        int reimbId = rs.getInt("reimb_id");
        int reimbAmount = rs.getInt("amount");
        String reimbDescription = rs.getString("description");

        Timestamp timestamp = rs.getTimestamp("resolved");
        String reimbSubDate;
        if(timestamp != null) {
          Date date = new Date(rs.getTimestamp("resolved").getTime());
          reimbSubDate = date.toString();
        } else {
          reimbSubDate = null;
        }
        Timestamp timestamp2 = rs.getTimestamp("resolved");
        String reimbResolvedDate;
        if(timestamp2 != null) {
          Date date2 = new Date(rs.getTimestamp("resolved").getTime());
          reimbResolvedDate = date2.toString();
        } else {
          reimbResolvedDate = null;
        }

        int type = rs.getInt("type");
        int status = rs.getInt("status");

        int eId = rs.getInt("reimb_author");
        String eUsername = rs.getString("e_username");
        String ePassword = rs.getString("e_password");
        String eUserFirstName = rs.getString("e_first_name");
        String eUserLastName = rs.getString("e_last_name");
        String eUserEmail = rs.getString("e_email");
        String eUserRole = "employee";

        User employee = new User(eId, eUsername, ePassword, eUserFirstName, eUserLastName, eUserEmail, eUserRole);

        int mId = rs.getInt("reimb_resolver");
        String mUsername = rs.getString("m_username");
        String mPassword = rs.getString("m_password");
        String mUserFirstName = rs.getString("m_first_name");
        String mUserLastName = rs.getString("m_last_name");
        String mUserEmail = rs.getString("m_email");
        String mUserRole = "finance manager";

        User manager = new User(mId, mUsername, mPassword, mUserFirstName, mUserLastName, mUserEmail, mUserRole);

        Reimbursement r = new Reimbursement(reimbId, reimbAmount, reimbDescription, reimbSubDate, reimbResolvedDate, employee, manager, type, status);
        reimbursements.add(r);
      }
      return reimbursements;
    }
  }
}
