package com.revature.service;

import com.revature.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JWTServiceTest {
  @Test
  public void testCreateJWTService() {
    User test = new User(99, "test", "test", "Jane", "Doe", "janedoe@test.com", "finance manager");
  }
}