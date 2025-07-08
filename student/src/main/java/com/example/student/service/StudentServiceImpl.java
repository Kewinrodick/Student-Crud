package com.example.student.service;

import com.example.student.dtos.StudentRequestDto;
import com.example.student.entity.Student;
import com.example.student.repository.StudentRepository;
import com.example.student.response.CommonResponse;
import com.example.student.studentEnum.StudentEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;


    @Override
    public CommonResponse addStudent(Student student) {
        CommonResponse response = new CommonResponse();
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
    public List<CommonResponse> findAllStudents() {
        return studentRepository.findAll().stream()
                .map(student -> {
                    CommonResponse response = new CommonResponse();
                    response.setStatus(StudentEnum.SUCCESS);
                    response.setSuccessMessage("Student found successfully");
                    response.setCode(200);
                    response.setData(student);

                    return response;
                })
                .toList();
    }

    @Override
    public CommonResponse getStudentById(String id) {
        CommonResponse response = new CommonResponse();
        try {
            Optional<Student> student = studentRepository.findById(id);
            if(student.isPresent()){
                response.setStatus(StudentEnum.SUCCESS);
                response.setSuccessMessage("Student found successfully");
                response.setCode(200);
                response.setData(student);
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
    public CommonResponse updateStudent(String id, StudentRequestDto studentRequestDto) {
        CommonResponse response = new CommonResponse();
        try{
            Student student = studentRepository.findById(id).orElse(null);
            if(student != null){
                student.setName(studentRequestDto.getName());
                student.setCourse(studentRequestDto.getCourse());
                student.setEmail(studentRequestDto.getEmail());
                student.setCurrentYear(studentRequestDto.getCurrentYear());
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
    public CommonResponse deleteStudent(String id) {
        CommonResponse response = new CommonResponse();
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
    public CommonResponse deleteAllStudents() {
        CommonResponse response = new CommonResponse();
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
