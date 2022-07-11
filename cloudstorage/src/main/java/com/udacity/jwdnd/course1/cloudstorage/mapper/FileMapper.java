package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    File getFile(String fileName);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata)" +
            " VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);

    @Update("UPDATE FILES SET filename=#{fileName},contenttype=#{contentType},filesize=#{fileSize},userid=#{userId},filedata=#{fileData} WHERE fileid=#{fileId}")
    void updateFile(File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
    void deleteFile(Integer fileId);
}
