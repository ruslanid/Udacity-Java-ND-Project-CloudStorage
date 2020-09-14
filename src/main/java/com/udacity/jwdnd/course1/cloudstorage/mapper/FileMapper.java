package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
  @Select("SELECT * FROM FILES WHERE user_id=#{userId}")
  List<File> findAll(Integer userId);

  @Select("SELECT * FROM FILES WHERE file_id=#{id}")
  File findById(Integer id);

  @Select("SELECT * FROM FILES WHERE file_name=#{name}")
  File findByName(String name);

  @Insert("INSERT INTO FILES (file_name, content_type, file_size, file_data, user_id) " +
      "VALUES (#{name}, #{contentType}, #{size}, #{data}, #{userId})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int insert(File file);

  @Delete("DELETE FROM FILES WHERE file_id=#{id}")
  int deleteById(Integer id);
}
