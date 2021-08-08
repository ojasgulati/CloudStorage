package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO files (filename, contenttype, filesize, userid, filedata) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);

    @Select("SELECT * FROM files WHERE userId = #{userId}")
    List<File> getFiles(int userId);

    @Select("SELECT * FROM files WHERE fileId = #{fileId} AND userId = #{userId}")
    File getFileById(int fileId, int userId);

    @Select("SELECT * FROM files WHERE fileName = #{fileName} AND userId = #{userId}")
    File getFileByName(String fileName, int userId);

    @Delete("DELETE FROM files WHERE fileId = #{fileId} AND userId = #{userId}")
    void deleteFile(int fileId, int userId);
}
