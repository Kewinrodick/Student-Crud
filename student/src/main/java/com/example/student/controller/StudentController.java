package com.example.student.controller;

import com.example.student.entity.Student;
import com.example.student.response.StudentResponse;
import com.example.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public StudentResponse create(@RequestBody Student student) {
       return studentService.addStudent(student);
    }
}
