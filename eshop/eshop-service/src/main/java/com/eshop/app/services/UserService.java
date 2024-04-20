package com.eshop.app.services;

import com.eshop.app.common.entities.rdbms.User;
import com.eshop.app.common.repositories.rdbms.master.UserRepository;
import com.eshop.app.models.req.UserLoginReqDTO;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private UserRepository userRepository;

    public void register(UserLoginReqDTO dto) {
        String salt = generateSalt();
        String passwordHash = hashPassword(dto.getPassword(), salt);
        User user = User.builder().username(dto.getUsername()).
                password(passwordHash)
                .version(1).isActive(Boolean.TRUE)
                .tenantId(dto.getTenantId())
                .role(dto.getRole())
                .email(dto.getEmail())
                .loginId(dto.getLoginId())
                .build();
        userRepository.save(user);
    }

    public boolean validateUser(UserLoginReqDTO dto) {
        User user = userRepository.findById(dto.getId()).orElse(null);
        if (user == null) return false;
        String[] parts = user.getPassword().split(":");
        String hash = hashPassword(dto.getPassword(), parts[1]);
        return hash.equals(parts[0]) && dto.getLoginId().equals(user.getLoginId());
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
