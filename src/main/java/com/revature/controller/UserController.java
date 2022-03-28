package com.revature.controller;

import com.revature.dto.LoginDTO;
import com.revature.model.User;
import com.revature.service.JWTService;
import com.revature.service.UserService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class UserController implements Controller {
  // do we need a user controller -- will it just be the login endpoint?

  private UserService userService;
  private JWTService jwsService;

  private Handler login = ctx -> {
    LoginDTO loginInfo = ctx.bodyAsClass(LoginDTO.class);
    User user = userService.login(loginInfo.getUsername(), loginInfo.getPassword());
    String jwt = this.jwsService.createJWT(user);

    ctx.header("Access-Control-Expose-Headers", "*");
    ctx.header("Token", jwt);
    ctx.json(user);
  };

  @Override
  public void mapEndpoints(Javalin app) {
  app.post("/login", login);
  }
}
