package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {
  private Integer id;
  private String name;
  private String contentType;
  private Long size;
  private Integer userId;
  private byte[] data;


  public File(Integer id, String name, String contentType, Long size, Integer userId, byte[] data) {
    this.id = id;
    this.name = name;
    this.contentType = contentType;
    this.size = size;
    this.userId = userId;
    this.data = data;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }
}
