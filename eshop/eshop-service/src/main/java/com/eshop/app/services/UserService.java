package com.eshop.app.services;

import com.eshop.app.common.entities.rdbms.User;
import com.eshop.app.common.repositories.rdbms.master.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void registerUser(String username, String password) {
        String salt = generateSalt();
        String passwordHash = hashPassword(password, salt);
        User user = new User();
        user.setUsername(username);
         user.setPassword(passwordHash + ":" + salt);
        userRepository.save(user);
    }

    public boolean validatePassword(String userId, String password) {
        User user = userRepository.findById(Long.getLong(userId)).orElse(null);
        if (user == null) return false;
        String[] parts = user.getPassword().split(":");
        String hash = hashPassword(password, parts[1]);
        return hash.equals(parts[0]);
    }

    private String generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private String hashPassword(String password, String salt) {
        return DigestUtils.md5Hex(password + salt);
    }
}
