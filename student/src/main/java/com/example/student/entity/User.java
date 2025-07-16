package com.example.student.entity;

import com.example.student.dtos.AuthRequest;
import com.example.student.studentEnum.Roles;
import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    @Indexed(unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Indexed(unique = true)
    @NotNull
    private Roles role;


    public User(AuthRequest userDto){
        this.name = userDto.getName();
        this.password = userDto.getPassword();
        this.role = userDto.getRole();
        this.email = userDto.getEmail();
    }
}
