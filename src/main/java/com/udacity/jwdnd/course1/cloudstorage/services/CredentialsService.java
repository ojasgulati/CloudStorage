package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {

    final UserService userService;
    final CredentialsMapper credentialsMapper;
    final EncryptionService encryptionService;

    public CredentialsService(UserService userService, CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }


    public List<Credential> getCredentials() {
        List<Credential> credentials =  credentialsMapper.getCredential(userService.getCurrentUserId());
        for(Credential credential : credentials){
            credential.setDecryptPassword(encryptionService.decryptValue(credential.getPassword(), credential.getCredKey()));
        }
        return credentials;
    }

    public void createCredential(CredentialForm credentialForm) {

        final String password = credentialForm.getPassword();
        final String credKey = getKey();
        String encryptedPassword = encryptionService.encryptValue(password, credKey);

        final Credential credential = new Credential();
        credential.setUsername(credentialForm.getUsername());
        credential.setUrl(credentialForm.getUrl());
        credential.setCredKey(credKey);
        credential.setPassword(encryptedPassword);
        credential.setUserId(userService.getCurrentUserId());
        credentialsMapper.insertCredential(credential);
    }

    public Credential getCredentialById(int id) {
        return credentialsMapper.getCredentialById(id, userService.getCurrentUserId());
    }

    public void updateCredential(int credentialId, CredentialForm credentialForm) {

        final String password = credentialForm.getPassword();
        final String credKey = getKey();
        String encryptedPassword = encryptionService.encryptValue(password, credKey);

        final Credential credential = new Credential();
        credential.setCredentialId(credentialId);
        credential.setUrl(credentialForm.getUrl());
        credential.setCredKey(credKey);
        credential.setPassword(encryptedPassword);
        credentialsMapper.updateCredential(credential);
    }

    public void deleteCredential(int credentialId) {
        credentialsMapper.deleteCredential(credentialId, userService.getCurrentUserId());
    }

    private String getKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
