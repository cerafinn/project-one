package com.revature.controller;

import com.revature.exception.ImageNotFoundException;
import com.revature.exception.InvalidImageException;
import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.FailedLoginException;

public class ExceptionController implements Controller {

//  private Logger logger = LoggerFactory.getLogger(ExceptionController.class);

  private ExceptionHandler<FailedLoginException> failedLogin = (exception, ctx) -> {
    ctx.status(400);
    ctx.json(exception.getMessage());
  };

  private ExceptionHandler<InvalidImageException> invalidImage = (exception, ctx) -> {
    ctx.status(400);
    ctx.json(exception.getMessage());
  };

  private ExceptionHandler<IllegalArgumentException> invalidArgument = (exception, ctx) -> {
    ctx.status(400);
    ctx.json(exception.getMessage());
  };

  private ExceptionHandler<ImageNotFoundException> imageNotFound = (exception, ctx) -> {
    ctx.status(404);
    ctx.json(exception.getMessage());
  };

  @Override
  public void mapEndpoints(Javalin app) {
    app.exception(FailedLoginException.class, failedLogin);
    app.exception(InvalidImageException.class, invalidImage);
    app.exception(IllegalArgumentException.class, invalidArgument);
    app.exception(ImageNotFoundException.class, imageNotFound);
  }
}
