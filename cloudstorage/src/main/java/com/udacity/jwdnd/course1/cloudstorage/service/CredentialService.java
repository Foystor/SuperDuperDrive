package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final UserMapper userMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, UserMapper userMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userMapper = userMapper;
        this.encryptionService = encryptionService;
    }

    public int createCredential(Credential credential) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userMapper.getUser(authentication.getName());

        // encrypt password
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        return credentialMapper.insertCredential(
                new Credential(null,
                        credential.getUrl(),
                        credential.getUsername(),
                        encodedKey,
                        encryptedPassword,
                        user.getUserId()));
    }

    public void updateCredential(Credential credential) {
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(),
                credentialMapper.getCredential(credential.getCredentialId()).getKey());
        credential.setPassword(encryptedPassword);

        credentialMapper.updateCredential(credential);
    }

    public List<Credential> getCredentialList(Integer userId) {
        return this.credentialMapper.getCredentialList(userId);
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }
}
