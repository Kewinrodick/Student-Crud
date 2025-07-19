package com.example.student.service;


import com.example.student.dtos.AuthRequest;
import com.example.student.dtos.LoginRequest;
import com.example.student.entity.User;
import com.example.student.response.CommonResponse;

import java.util.List;

public interface UserService {
    public CommonResponse registerUser(AuthRequest authRequest);
    public CommonResponse loginUser(LoginRequest loginRequest);
}
