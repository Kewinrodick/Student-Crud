package com.example.student.security;

import com.example.student.entity.User;
import com.example.student.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        try {
            Optional<User> user = userRepository.findByName(name);
            if (user.isPresent()) {
                return org.springframework.security.core.userdetails.User.builder()
                        .username(user.get().getName())
                        .password(user.get().getPassword())
                        .roles(user.get().getRole().toString())
                        .build();
            }
        }catch (Exception e){
            throw new UsernameNotFoundException(e.getMessage());
        }
        return null;
    }
}
