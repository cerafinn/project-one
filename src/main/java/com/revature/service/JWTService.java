package com.revature.service;

import java.security.Key;

public class JWTService {

  private static JWTService instance;
  private Key key;

  private JWTService() {
    byte[] secret = "my_secret_secret".getBytes();
    key = Keys.hmacShaKeyFor(secret);
  }

  public JWTService getInstance() {
    if(JWTService.instance == null) {
      JWTService.instance = new JWTService();
    }
  }
}
