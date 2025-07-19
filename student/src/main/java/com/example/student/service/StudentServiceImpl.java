package com.example.student.service;

import com.example.student.dtos.StudentRequestDto;
import com.example.student.entity.Student;
import com.example.student.entity.User;
import com.example.student.repository.StudentRepository;
import com.example.student.repository.UserRepository;
import com.example.student.response.CommonResponse;
import com.example.student.studentEnum.ResponseStatus;
import com.example.student.studentEnum.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public CommonResponse addStudent(Student student) {
        CommonResponse response = new CommonResponse();
        try {
            User user = new User();
            studentRepository.save(student);
            response.setStatus(ResponseStatus.SUCCESS);
            response.setSuccessMessage("Student added successfully");
            response.setCode(201);
            response.setData(student);
        }catch (Exception e){
            response.setStatus(ResponseStatus.FAILED);
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
                    response.setStatus(ResponseStatus.SUCCESS);
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
            if(student.isPresent()) {
                //If Student - he must be able to see his own detaild not others (as two users will not have same name)
                /*String name = SecurityContextHolder.getContext().getAuthentication().getName();
                Optional<User> user = userRepository.findByName(name);
                if (user.get().getRole().equals(Roles.STUDENT)) {
                    if (!user.get().getName().equals(student.get().getName())) {
                        throw new Exception("Unauthorized user");
                    } else {
                        response.setStatus(ResponseStatus.SUCCESS);
                        response.setSuccessMessage("Student found successfully");
                        response.setCode(200);
                        response.setData(student);
                    }
                }*/
                response.setStatus(ResponseStatus.SUCCESS);
                response.setSuccessMessage("Student found successfully");
                response.setCode(200);
                response.setData(student.get());
            }
            else{
                throw new Exception("Student not found");
            }

        }catch (Exception e){
            response.setStatus(ResponseStatus.FAILED);
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
                response.setStatus(ResponseStatus.SUCCESS);
                response.setSuccessMessage("Student updated successfully");
                response.setCode(200);
                response.setData(student);
            }
            else{
                throw new Exception("Student not found");
            }
        }catch (Exception e){
            response.setStatus(ResponseStatus.FAILED);
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
                response.setStatus(ResponseStatus.SUCCESS);
                response.setSuccessMessage("Student deleted successfully");
                response.setCode(204);
            }
            else{
                throw new Exception("Student not found");
            }
        }catch (Exception e){
            response.setStatus(ResponseStatus.FAILED);
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
            response.setStatus(ResponseStatus.SUCCESS);
            response.setSuccessMessage("All students deleted successfully");
            response.setCode(204);
        }catch (Exception e){
            response.setStatus(ResponseStatus.FAILED);
            response.setErrorMessage(e.getMessage());
            response.setCode(500);
        }
        return response;
    }

}
