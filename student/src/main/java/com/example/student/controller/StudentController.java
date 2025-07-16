package com.example.student.controller;

import com.example.student.dtos.StudentRequestDto;
import com.example.student.entity.Student;
import com.example.student.response.CommonResponse;
import com.example.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CommonResponse> create(@RequestBody Student student) {
        CommonResponse response = studentService.addStudent(student);
       return ResponseEntity.status(response.getCode()).body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<CommonResponse>> getAll() {
        List<CommonResponse> response = studentService.findAllStudents();
        return ResponseEntity.status(200).body(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @GetMapping("/{id}")
    public  ResponseEntity<CommonResponse> getById(@PathVariable String id) {
        CommonResponse response = studentService.getStudentById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable String id, @RequestBody StudentRequestDto studentRequestDto) {
        CommonResponse response = studentService.updateStudent(id,studentRequestDto);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        CommonResponse response = studentService.deleteStudent(id);
        return ResponseEntity.status(response.getCode()).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        CommonResponse response = studentService.deleteAllStudents();
        return ResponseEntity.status(response.getCode()).build();
    }

}
