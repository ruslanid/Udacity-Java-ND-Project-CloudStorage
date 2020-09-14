package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

  private CredentialMapper credentialMapper;
  private EncryptionService encryptionService;

  public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
    this.credentialMapper = credentialMapper;
    this.encryptionService = encryptionService;
  }

  public List<Credential> getAllCredentials(User currentUser) {
    return credentialMapper.findAll(currentUser.getId());
  }

  public String getDecryptedPassword(Integer id) {
    Credential credential = credentialMapper.findById(id);
    return encryptionService.decryptValue(credential.getPassword(), credential.getKey());
  }

  public int addCredential(Credential credential, User currentUser) {
    String[] secureData = encryptPassword(credential.getPassword());
    credential.setKey(secureData[0]);
    credential.setPassword(secureData[1]);
    credential.setUserId(currentUser.getId());
    return credentialMapper.insert(credential);
  }

  public int updateCredential(Credential credential, User currentUser) {
    if (credential.getUserId().equals(currentUser.getId())) {
      String[] secureData = encryptPassword(credential.getPassword());
      credential.setKey(secureData[0]);
      credential.setPassword(secureData[1]);
      return credentialMapper.update(credential);
    }
    return 0;
  }

  public int deleteCredential(Integer id, User currentUser) {
    Credential credential = credentialMapper.findById(id);
    if (credential.getUserId().equals(currentUser.getId())) {
      return credentialMapper.deleteById(id);
    }
    return 0;
  }

  private String[] encryptPassword(String password) {
    SecureRandom random = new SecureRandom();
    byte[] key = new byte[16];
    random.nextBytes(key);
    String encodedKey = Base64.getEncoder().encodeToString(key);
    String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
    return new String[] { encodedKey, encryptedPassword };
  }

}
