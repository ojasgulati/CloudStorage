package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO files (filename, contenttype, filesize, userid, filedata) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);

    @Select("SELECT * FROM files")
    List<File> getFiles();

    @Select("SELECT * FROM files WHERE fileId = #{fileId}")
    File getFileById(int fileId);

    @Select("SELECT * FROM files WHERE fileName = #{fileName}")
    File getFileByName(String fileName);

    @Delete("DELETE FROM files WHERE fileId = #{fileId}")
    void deleteFile(int fileId);
}
