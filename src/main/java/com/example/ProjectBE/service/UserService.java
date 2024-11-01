package com.example.ProjectBE.service;

import com.example.ProjectBE.dto.request.UserDTO.UserCreationRequest;
import com.example.ProjectBE.entities.User;
import com.example.ProjectBE.payload.request.LoginRequest;
import com.example.ProjectBE.repository.UserRepository;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Login Method

    public User loginMethod(String userName, String passWord) {
        Optional<User> userOptional = userRepository.findByUserName(userName);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (BCrypt.checkpw(passWord, user.getPassWord())) {
                return user;
            }
        }

        return null;
    }

    // Register Method
    public User registerUser(UserCreationRequest req) {
        User user = new User();
        user.setFirstName(req.getFirstName());
        user.setUserName(req.getUserName());
        String hashedPassword = BCrypt.hashpw(req.getPassWord(), BCrypt.gensalt());
        user.setPassWord(hashedPassword);
        user.setRole(req.getRole());
        user.setLastName("Edit yourLastName");
        user.setAddress("Edit yourAddress");
        user.setPhoneNumber("Edit yourPhoneNumber");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dobDate = simpleDateFormat.parse("2003-01-01");
            user.setUserDob(dobDate);
        } catch (ParseException e) {
            System.out.println(e);
        }
        user.setAvatar("Edit your avatar");
        user.setStatus(true);
        return userRepository.save(user);
    }

    public User getUserById(int id) {
        return userRepository.getReferenceById(id);
    }
}
