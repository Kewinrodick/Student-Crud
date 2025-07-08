package com.example.student.controller;

import com.example.student.dtos.StudentRequestDto;
import com.example.student.entity.Student;
import com.example.student.response.CommonResponse;
import com.example.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<CommonResponse> create(@RequestBody Student student) {
        CommonResponse response = studentService.addStudent(student);
       return ResponseEntity.status(response.getCode()).body(response);
    }
    @GetMapping
    public ResponseEntity<List<CommonResponse>> getAll() {
        List<CommonResponse> response = studentService.findAllStudents();
        return ResponseEntity.status(200).body(response);
    }
    @GetMapping("/{id}")
    public  ResponseEntity<CommonResponse> getById(@PathVariable String id) {
        CommonResponse response = studentService.getStudentById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable String id, @RequestBody StudentRequestDto studentRequestDto) {
        CommonResponse response = studentService.updateStudent(id,studentRequestDto);
        return ResponseEntity.status(response.getCode()).body(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        CommonResponse response = studentService.deleteStudent(id);
        return ResponseEntity.status(response.getCode()).build();
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        CommonResponse response = studentService.deleteAllStudents();
        return ResponseEntity.status(response.getCode()).build();
    }


}
