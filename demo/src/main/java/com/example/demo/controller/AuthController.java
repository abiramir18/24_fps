package com.example.demo.controller;

import com.example.demo.repository.UserRepository;
import com.example.demo.model.*;
import com.example.demo.service.*;
import com.example.demo.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserRecord user){
               String result = userService.registerUser(user);
               if(result.contains("Error")){
                   return ResponseEntity.badRequest().body(Map.of("message", result));
               }

               String token = jwtUtils.generateToken(user.getUsername());
               return ResponseEntity.ok(Map.of(
                       "token", token,
                       "name", user.getName(),
                       "username", user.getUsername()
               ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRecord loginRequest){
        Optional<UserRecord> user = userService.loginUser(loginRequest.getUsername(),
                loginRequest.getPassword());

        if(user.isPresent()){
            UserRecord foundUser = user.get();
            String token = jwtUtils.generateToken(foundUser.getUsername());

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "username", foundUser.getUsername(),
                    "name", foundUser.getName()
            ));
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid username or password"));
        }

//        if(result.contains("Error")){
//            return ResponseEntity.badRequest().body(result);
//        }
//        return ResponseEntity.ok(result);
    }


}