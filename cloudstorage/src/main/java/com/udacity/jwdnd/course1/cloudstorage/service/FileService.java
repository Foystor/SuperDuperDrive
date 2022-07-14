package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;
    private final UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public boolean isFileNameAvailable(String fileName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userMapper.getUser(authentication.getName()).getUserId();

        boolean fileNameNotExist = true;
        for (File file : getFileList(userId)) {
            if (file.getFileName().matches(fileName)) {
                fileNameNotExist = false;
                break;
            }
        }
        return fileNameNotExist;
    }

    public int createFile(MultipartFile file) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userMapper.getUser(authentication.getName());

        return fileMapper.insertFile(
                new File(null,
                        file.getOriginalFilename(),
                        file.getContentType(),
                        file.getSize() + "",
                        user.getUserId(),
                        file.getBytes()));
    }

    public List<File> getFileList(Integer userId) {
        return this.fileMapper.getFileList(userId);
    }

    public File getFile(String fileName) {
        return this.fileMapper.getFile(fileName);
    }

    public void deleteFile(String fileName) {
        fileMapper.deleteFile(fileName);
    }
}
