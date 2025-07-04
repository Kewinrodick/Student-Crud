package com.example.student.response;

import com.example.student.studentEnum.StudentEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentResponse {
    private StudentEnum status;
    private String errorMessage;
    private String successMessage;
    private Object data;
    private int code;
}
