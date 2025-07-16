package com.example.student.service;

import com.example.student.dtos.AuthRequest;
import com.example.student.entity.User;
import com.example.student.repository.UserRepository;
import com.example.student.response.CommonResponse;
import com.example.student.security.CustomUserDetailService;
import com.example.student.security.SecurityConfig;
import com.example.student.studentEnum.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public CommonResponse registerUser(AuthRequest authRequest) {
        CommonResponse response = new CommonResponse();
        try {

            if (userRepository.findByName(authRequest.getName()).isPresent()) {
                throw new Exception(authRequest.getName() + " already exists");
            }
            if(userRepository.existsByEmail(authRequest.getEmail())){
                throw new Exception(authRequest.getEmail() + " already exists");
            }
            // if user not exists then create one and save
            String encryptedPass = securityConfig.bCryptPasswordEncoder().encode(authRequest.getPassword());
            authRequest.setPassword(encryptedPass);
            User user = new User(authRequest);

            userRepository.save(user);
            response.setStatus(ResponseStatus.SUCCESS);
            response.setSuccessMessage("user created and saved successfully");
            response.setCode(201);
            response.setData(user);

        }
        catch (Exception e) {
            response.setStatus(ResponseStatus.FAILED);
            response.setErrorMessage(e.getMessage());
            response.setCode(401);
        }
        return response;
    }

    @Override
    public CommonResponse loginUser(AuthRequest authRequest) {
        CommonResponse response = new CommonResponse();
        try{
            Optional<User> user1 = userRepository.findByName(authRequest.getName());
            if(user1.isEmpty()){
                System.out.println(user1);
                throw new Exception("user not found");
            }
            User user = user1.get();
            if (!user.getRole().equals(authRequest.getRole())){
                System.out.println(user.getRole());
                System.out.println(authRequest.getRole());
                throw new Exception("role not match");
            }
                Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));
            if(authentication.isAuthenticated()){
                String token = jwtUtils.generateJwtToken(userDetailsService.loadUserByUsername(authRequest.getName()));

                response.setStatus(ResponseStatus.SUCCESS);
                response.setSuccessMessage("Token :"+token);
                response.setCode(200);
                response.setData(authRequest);
            }
            else{
                throw new Exception("user not authenticated");
            }


        }catch (Exception e) {
           response.setStatus(ResponseStatus.FAILED);
           response.setErrorMessage(e.getMessage());
           response.setCode(401);
        }
        return response;
    }
}
