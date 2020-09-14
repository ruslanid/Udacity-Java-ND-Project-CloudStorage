package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

  private UserService userService;
  private FileService fileService;
  private NoteService noteService;
  private CredentialService credentialService;

  public HomeController(FileService fileService, UserService userService, NoteService noteService, CredentialService credentialService) {
    this.fileService = fileService;
    this.userService = userService;
    this.noteService = noteService;
    this.credentialService = credentialService;
  }

  @GetMapping
  public String getHomePage(Authentication authentication, Model model) {
    User currentUser = userService.getUser(authentication.getName());
    List<File> files = fileService.getAllFiles(currentUser);
    List<Note> notes = noteService.getAllNotes(currentUser);
    List<Credential> credentials = credentialService.getAllCredentials(currentUser);
    model.addAttribute("files", files);
    model.addAttribute("notes", notes);
    model.addAttribute("credentials", credentials);
    return "home";
  }

}
