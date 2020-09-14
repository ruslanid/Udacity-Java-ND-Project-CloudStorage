package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

  private FileMapper fileMapper;

  public FileService(FileMapper fileMapper) {
    this.fileMapper = fileMapper;
  }

  public List<File> getAllFiles(User currentUser) {
    return fileMapper.findAll(currentUser.getId());
  }

  public File getFile(Integer id) {
    return fileMapper.findById(id);
  }

  public boolean isFileNameAvailable(String fileName) {
    return fileMapper.findByName(fileName) == null;
  }

  public int uploadFile(MultipartFile fileUpload, User currentUser) throws IOException {
    byte[] data = fileUpload.getBytes();
    File newFile = new File(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(), fileUpload.getSize(), currentUser.getId(), data);
    return fileMapper.insert(newFile);
  }

  public int deleteFile(Integer id, User currentUser) {
    File file = fileMapper.findById(id);
    System.out.println(file);
    if (file.getUserId().equals(currentUser.getId())) {
      return fileMapper.deleteById(id);
    }
    return 0;
  }

}
