package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/notes")
public class NoteController {

  private UserService userService;
  private NoteService noteService;

  public NoteController(UserService userService, NoteService noteService) {
    this.userService = userService;
    this.noteService = noteService;
  }

  @PostMapping("/save")
  public String saveNote(
      @ModelAttribute Note note, Authentication authentication, RedirectAttributes ra) {

    boolean dbOperationSuccess = false;

    User currentUser = userService.getUser(authentication.getName());
    if (note.getId() == null) {
      if (noteService.addNote(note, currentUser) == 1) {
        dbOperationSuccess = true;
      }
    } else {
      if (noteService.updateNote(note, currentUser) == 1) {
        dbOperationSuccess = true;
      }
    }

    ra.addAttribute("dbOperationSuccess", dbOperationSuccess);

    return "redirect:/results";
  }

  @GetMapping("/delete/{id}")
  public String deleteNote(
      @PathVariable Integer id, Authentication authentication, RedirectAttributes ra) {

    boolean dbOperationSuccess = false;

    User currentUser = userService.getUser(authentication.getName());
    if (noteService.deleteNote(id, currentUser) == 1) {
      dbOperationSuccess = true;
    }

    ra.addAttribute("dbOperationSuccess", dbOperationSuccess);

    return "redirect:/results";
  }

}
