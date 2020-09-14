package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

  @RequestMapping("/error")
  public String handleError(HttpServletRequest request, Model model) {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    String errorMessage = "Something went wrong.";

    if (status != null) {
      int statusCode = Integer.parseInt(status.toString());

      if(statusCode > 499) {
        errorMessage = "Something went wrong on our end. Please try again later.";
      }
      else if(statusCode > 399) {
        errorMessage = "You were either trying to access the resource that doesn't exist or you have no authority to view.";
      }
    }

    model.addAttribute("errorMessage", errorMessage);

    return "error";
  }

  @Override
  public String getErrorPath() {
    return null;
  }

}
