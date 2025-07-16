package com.example.student.service;

import com.example.student.dtos.StudentRequestDto;
import com.example.student.entity.Student;
import com.example.student.response.CommonResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
   public CommonResponse addStudent(Student student);
   public List<CommonResponse> findAllStudents();
   public CommonResponse getStudentById(String id);
   public CommonResponse updateStudent(String id, StudentRequestDto studentRequestDto);
   public CommonResponse deleteStudent(String id);
   public CommonResponse deleteAllStudents();
}
