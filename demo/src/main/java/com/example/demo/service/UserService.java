package com.example.demo.service;

import com.example.demo.SecurityConfig;
import com.example.demo.repository.*;
import com.example.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService{

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public String registerUser(UserRecord user){

        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            return "Error: Username is already taken!";
        }

        String hashpassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashpassword);

        if(user.getRoles() == null || user.getRoles().isEmpty()){
            user.setRoles("customer");
        }

        userRepository.save(user);

        return "User registered successfully with role : "+ user.getRoles();
    }

    public Optional<UserRecord> loginUser(String username, String rawPassword){
        Optional<UserRecord> existingUser = userRepository.findByUsername(username);

        if(existingUser.isPresent() && passwordEncoder.matches(rawPassword, existingUser.get().getPassword())){
            return existingUser;
        }
        return Optional.empty();

//        if(existingUser.isPresent()){
//            if(passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())){
//                return "Login successful! Welcome "+user.getUsername();
//            }
//            else{
//                return "Error: Invalid password!";
//            }
//        }
//        return "Error: User not found";
    }
}