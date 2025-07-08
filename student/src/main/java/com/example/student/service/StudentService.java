package com.example.student.service;

import com.example.student.dtos.StudentRequestDto;
import com.example.student.entity.Student;
import com.example.student.response.StudentResponse;

import java.util.List;

public interface StudentService {
   public StudentResponse addStudent(Student student);
   public List<StudentResponse> findAllStudents();
   public StudentResponse getStudentById(String id);
   public StudentResponse updateStudent(String id, StudentRequestDto studentRequestDto);
   public StudentResponse deleteStudent(String id);
   public StudentResponse deleteAllStudents();
}
