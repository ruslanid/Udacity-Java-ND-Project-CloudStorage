package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

  private NoteMapper noteMapper;

  public NoteService(NoteMapper noteMapper) {
    this.noteMapper = noteMapper;
  }

  public List<Note> getAllNotes(User currentUser) {
    return noteMapper.findAll(currentUser.getId());
  }

  public int addNote(Note note, User currentUser) {
    note.setUserId(currentUser.getId());
    return noteMapper.insert(note);
  }

  public int updateNote(Note note, User currentUser) {
    if (note.getUserId().equals(currentUser.getId())) {
      return noteMapper.update(note);
    }
    return 0;
  }

  public int deleteNote(Integer id, User currentUser) {
    Note note = noteMapper.findById(id);
    if (note.getUserId().equals(currentUser.getId())) {
      return noteMapper.deleteById(id);
    }
    return 0;
  }
}
