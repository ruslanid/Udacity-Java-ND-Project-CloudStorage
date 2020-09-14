package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/files")
public class FileController {

  private UserService userService;
  private FileService fileService;

  public FileController(UserService userService, FileService fileService) {
    this.userService = userService;
    this.fileService = fileService;
  }

  @PostMapping("/upload")
  public String uploadFile(
      @RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, RedirectAttributes ra, Model model) throws IOException {

    if (fileUpload.getSize() > 0) {
      if (!fileService.isFileNameAvailable(fileUpload.getOriginalFilename())) {
        ra.addAttribute("fileUploadError", "A file with this name already exists in your list.");
      } else {
        boolean dbOperationSuccess = false;

        User currentUser = userService.getUser(authentication.getName());
        if (fileService.uploadFile(fileUpload, currentUser) == 1) {
          dbOperationSuccess = true;
        }

        ra.addAttribute("dbOperationSuccess", dbOperationSuccess);
        return "redirect:/results";
      }
    }

    return "redirect:/home";
  }

  @GetMapping("/download/{id}")
  public ResponseEntity<byte[]> downloadFile(@PathVariable Integer id) {
    File file = fileService.getFile(id);
    return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .body(file.getData());
  }

  @GetMapping("/delete/{id}")
  public String deleteFile(
      @PathVariable Integer id, Authentication authentication, RedirectAttributes ra) {

    boolean dbOperationSuccess = false;

    User currentUser = userService.getUser(authentication.getName());
    if (fileService.deleteFile(id, currentUser) == 1) {
      dbOperationSuccess = true;
    }

    ra.addAttribute("dbOperationSuccess", dbOperationSuccess);
    return "redirect:/results";
  }

}
