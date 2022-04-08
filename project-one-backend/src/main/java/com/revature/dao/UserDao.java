package com.revature.dao;

import com.revature.model.User;
import com.revature.utility.ConnectionUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
  public UserDao() {}

  public User getUserByLogin(String username, String password) throws SQLException {
    try (Connection conn = ConnectionUtility.getConnection()) {
      String sql = "SELECT users.id, users.username, users.password, users.user_first_name, users.user_last_name, users.user_email, " +
          "user_roles.role FROM users INNER JOIN user_roles ON users.user_role_id = user_roles.id " +
          "WHERE users.username = ? AND users.password = ?";

      try(PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        if(rs.next()) {
          int userId = rs.getInt("id");
          String user = rs.getString("username");
          String pswd = rs.getString("password");
          String firstName = rs.getString("user_first_name");
          String lastName = rs.getString("user_last_name");
          String userEmail = rs.getString("user_email");
          String roleId = rs.getString("role");

          return new User(userId, user, pswd, firstName, lastName, userEmail, roleId);
        }
        return null;
      }
    }
  }
}