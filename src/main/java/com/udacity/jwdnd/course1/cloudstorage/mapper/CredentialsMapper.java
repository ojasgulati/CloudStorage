package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {

    @Insert("INSERT INTO credentials (url, username, cred_key, password, userid) VALUES (#{url}, #{username}, #{credKey}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);

    @Update("UPDATE credentials SET url = #{url}, username = #{username}, cred_key = #{credKey}, password = #{password} WHERE credentialid = #{credentialId}")
    void updateCredential(Credential credential);

    @Select("SELECT credentialid, url, username, cred_key AS credKey, password FROM credentials WHERE userid = #{userId}")
    List<Credential> getCredential(int userId);

    @Select("SELECT * FROM credentials WHERE credentialid = #{credentialId} AND userId = #{userId}")
    Credential getCredentialById(int credentialId, int userId);

    @Delete("DELETE FROM credentials WHERE credentialid = #{credentialId} AND userId = #{userId}")
    void deleteCredential(int credentialId, int userId);

}
