package com.example.student.service;

import com.example.student.entity.Student;
import com.example.student.repository.StudentRepository;
import com.example.student.response.StudentResponse;
import com.example.student.studentEnum.StudentEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    @Override
    public StudentResponse addStudent(Student student) {
        StudentResponse response = new StudentResponse();
        try {
            studentRepository.save(student);
            response.setStatus(StudentEnum.SUCCESS);
            response.setSuccessMessage("Student added successfully");
            response.setCode(201);
            response.setData(student);
        }catch (Exception e){
            response.setStatus(StudentEnum.FAILED);
            response.setErrorMessage(e.getMessage());
            response.setCode(500);
        }
        return response;
    }

    @Override
    public List<StudentResponse> findAllStudents() {
        return studentRepository.findAll().stream()
                .map(student -> {
                    StudentResponse response = new StudentResponse();
                    response.setStatus(StudentEnum.SUCCESS);
                    response.setSuccessMessage("Student found successfully");
                    response.setCode(200);
                    response.setData(student);

                    return response;
                })
                .toList();
    }

    @Override
    public StudentResponse getStudentById(String id) {
        StudentResponse response = new StudentResponse();
        try {
            Optional<Student> student = studentRepository.findById(id);
            if(student.isPresent()){
                response.setStatus(StudentEnum.SUCCESS);
                response.setSuccessMessage("Student found successfully");
                response.setCode(200);
                response.setData(student.get());
            }else{
                throw new Exception("Student not found");
            }

        }catch (Exception e){
            response.setStatus(StudentEnum.FAILED);
            response.setErrorMessage(e.getMessage());
            response.setCode(404);
        }
        return response;
    }

    @Override
    public StudentResponse updateStudent(Student student) {
        StudentResponse response = new StudentResponse();
        try{
            Optional<Student> stud = studentRepository.findById(student.getId());
            if(stud.isPresent()){
                studentRepository.save(student);
                response.setStatus(StudentEnum.SUCCESS);
                response.setSuccessMessage("Student updated successfully");
                response.setCode(200);
                response.setData(student);
            }
            else{
                throw new Exception("Student not found");
            }
        }catch (Exception e){
            response.setStatus(StudentEnum.FAILED);
            response.setErrorMessage(e.getMessage());
            response.setCode(404);
        }
        return response;
    }

    @Override
    public StudentResponse deleteStudent(String id) {
        StudentResponse response = new StudentResponse();
        try{
            if(studentRepository.findById(id).isPresent()){
                studentRepository.deleteById(id);
                response.setStatus(StudentEnum.SUCCESS);
                response.setSuccessMessage("Student deleted successfully");
                response.setCode(204);
            }
            else{
                throw new Exception("Student not found");
            }
        }catch (Exception e){
            response.setStatus(StudentEnum.FAILED);
            response.setErrorMessage(e.getMessage());
            response.setCode(404);
        }
        return response;
    }

    @Override
    public StudentResponse deleteAllStudents() {
        StudentResponse response = new StudentResponse();
        try{
            studentRepository.deleteAll();
            response.setStatus(StudentEnum.SUCCESS);
            response.setSuccessMessage("All students deleted successfully");
            response.setCode(204);
        }catch (Exception e){
            response.setStatus(StudentEnum.FAILED);
            response.setErrorMessage(e.getMessage());
            response.setCode(500);
        }
        return response;
    }


}
