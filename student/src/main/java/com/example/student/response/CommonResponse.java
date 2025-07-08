package com.example.student.response;

import com.example.student.studentEnum.StudentEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommonResponse {
    private StudentEnum status;
    private String errorMessage;
    private String successMessage;
    private Object data;
    private int code;
}
