package com.example.student.mapper;

import com.example.student.dtos.StudentRequestDto;
import com.example.student.entity.Student;
import org.mapstruct    .Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    Student toStudent(StudentRequestDto requestDto);
    void update(@MappingTarget Student student,StudentRequestDto requestDto);
}
