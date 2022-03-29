package com.revature.main;

import com.revature.controller.Controller;
import com.revature.controller.ExceptionController;
import com.revature.controller.ReimbursementController;
import com.revature.controller.UserController;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Driver {
  //create server methods for controller endpoints
  public static Logger logger = LoggerFactory.getLogger(Driver.class);

  public static void main(String[] args) {
    Javalin app = Javalin.create((config) -> {
      config.enableCorsForAllOrigins();
    });

    app.before((ctx) -> { logger.info(ctx.method() + " request received for " + ctx.path()); });
    mapControllers(app, new UserController(), new ReimbursementController(), new ExceptionController());
    app.start(8081);
  }

  public static void mapControllers(Javalin app, Controller... controllers) {
    for (Controller c : controllers) {
      c.mapEndpoints(app);
    }
  }
}
