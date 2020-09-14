package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

  private UserService userService;
  private CredentialService credentialService;

  public CredentialController(UserService userService, CredentialService credentialService) {
    this.userService = userService;
    this.credentialService = credentialService;
  }

  @PostMapping("/save")
  public String saveCredential(
      @ModelAttribute Credential credential, Authentication authentication, RedirectAttributes ra) {

    boolean dbOperationSuccess = false;

    User currentUser = userService.getUser(authentication.getName());
    if (credential.getId() == null) {
      if (credentialService.addCredential(credential, currentUser) == 1) {
        dbOperationSuccess = true;
      }
    } else {
      if (credentialService.updateCredential(credential, currentUser) == 1) {
        dbOperationSuccess = true;
      }
    }

    ra.addAttribute("dbOperationSuccess", dbOperationSuccess);

    return "redirect:/results";
  }

  @GetMapping(value = "/decrypt-password/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, String> getDecryptedPassword(@PathVariable Integer id) {
    return new HashMap<>() {{ put("decryptedPassword", credentialService.getDecryptedPassword(id)); }};
  }

  @GetMapping("/delete/{id}")
  public String deleteCredential(
      @PathVariable Integer id, Authentication authentication, RedirectAttributes ra) {

    boolean dbOperationSuccess = false;

    User currentUser = userService.getUser(authentication.getName());
    if (credentialService.deleteCredential(id, currentUser) == 1) {
      dbOperationSuccess = true;
    }

    ra.addAttribute("dbOperationSuccess", dbOperationSuccess);
    return "redirect:/results";
  }

}
