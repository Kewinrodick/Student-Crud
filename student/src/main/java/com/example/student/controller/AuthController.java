package com.example.student.controller;

import com.example.student.dtos.AuthRequest;
import com.example.student.dtos.LoginRequest;
import com.example.student.response.CommonResponse;
import com.example.student.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse> signup(@RequestBody @Validated AuthRequest authRequest) {
        System.out.println("in /signup");
        CommonResponse response = userService.registerUser(authRequest);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse> login(@RequestBody LoginRequest loginRequest) {
            System.out.println("in /login");
           CommonResponse response = userService.loginUser(loginRequest);
           return ResponseEntity.status(response.getCode()).body(response);
    }

}
