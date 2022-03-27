package com.revature.model;

import java.util.Objects;

public class User {

  private int id;
  private String username;
  private String password;
  private String userFirstName;
  private String userLastName;
  private String userEmail;
  private String userRole;

  public User() {
  }

  public User(int id, String username, String password, String userFirstName, String userLastName, String userEmail, String userRole) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.userFirstName = userFirstName;
    this.userLastName = userLastName;
    this.userEmail = userEmail;
    this.userRole = userRole;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUserFirstName() {
    return userFirstName;
  }

  public void setUserFirstName(String userFirstName) {
    this.userFirstName = userFirstName;
  }

  public String getUserLastName() {
    return userLastName;
  }

  public void setUserLastName(String userLastName) {
    this.userLastName = userLastName;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public String getUserRole() {
    return userRole;
  }

  public void setUserRole(String userRole) {
    this.userRole = userRole;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if(o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return id == user.id && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(userFirstName, user.userFirstName) && Objects.equals(userLastName, user.userLastName)  && Objects.equals(userEmail, user.userEmail) && Objects.equals(userRole, user.userRole);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, password, userFirstName, userLastName, userEmail, userRole);
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", userFirstName='" + userFirstName + '\'' +
        ", userLastName='" + userLastName + '\'' +
        ", userEmail='" + userEmail + '\'' +
        ", userRole='" + userRole + '\'' +
        '}';
  }
}
