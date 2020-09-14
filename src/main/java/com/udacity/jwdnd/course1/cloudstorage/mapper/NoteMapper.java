package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
  @Select("SELECT * FROM NOTES WHERE user_id=#{userId}")
  List<Note> findAll(Integer userId);

  @Select("SELECT * FROM NOTES WHERE note_id=#{id}")
  Note findById(Integer id);

  @Insert("INSERT INTO NOTES (note_title, note_description, user_id) " +
      "VALUES (#{title}, #{description}, #{userId})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int insert(Note note);

  @Update("UPDATE NOTES SET note_title=#{title}, note_description=#{description} " +
      "WHERE note_id=#{id}")
  int update(Note note);

  @Delete("DELETE FROM NOTES WHERE note_id=#{id}")
  int deleteById(Integer id);
}
