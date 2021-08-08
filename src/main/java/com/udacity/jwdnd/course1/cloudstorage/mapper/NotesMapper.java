package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {

    @Insert("INSERT INTO notes (notetitle, notedescription, userid) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(Note note);

    @Update("UPDATE notes SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}")
    void updateNote(Note note);

    @Select("SELECT * FROM notes WHERE userId = #{userId}")
    List<Note> getNotes(int userId);

    @Select("SELECT * FROM notes WHERE noteId = #{noteId} AND userId = #{userId}")
    Note getNoteById(int noteId, int userId);

    @Delete("DELETE FROM notes WHERE noteId = #{noteId} AND userId = #{userId}")
    void deleteNote(int noteId, int userId);

}
