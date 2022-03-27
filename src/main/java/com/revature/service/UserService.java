package com.revature.service;

import com.revature.dao.UserDao;

public class UserService {
  private UserDao userDao;

  public UserService() {
    this.userDao = new UserDao();
  }

  public UserService(UserDao mockDao) {
    this.userDao = mockDao;
  }
}
