package com.example.student.service;

import com.example.student.entity.Student;
import com.example.student.response.StudentResponse;

import java.util.List;

public interface StudentService {
   public StudentResponse addStudent(Student student);
   public List<StudentResponse> findAllStudents();
}
