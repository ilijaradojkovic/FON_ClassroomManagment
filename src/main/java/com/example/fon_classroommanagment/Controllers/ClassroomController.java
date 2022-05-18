package com.example.fon_classroommanagment.Controllers;

import com.example.fon_classroommanagment.Exceptions.ClassroomExistsException;
import com.example.fon_classroommanagment.Exceptions.UserExistsExcetion;
import com.example.fon_classroommanagment.Models.Classroom.Classroom;
import com.example.fon_classroommanagment.Models.DTO.FilterDTO;
import com.example.fon_classroommanagment.Models.DTO.SearchClassroomDTO;
import com.example.fon_classroommanagment.Services.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ClassroomController {

    @Autowired
    private ClassroomService service;

    @GetMapping("/filter")
    public ResponseEntity<String> filter(@RequestBody @Valid FilterDTO filterDTO){
        System.out.println(service.filter(filterDTO));
        return   ResponseEntity.ok("");
    }


    @PostMapping("/searchClassroom")
    public ResponseEntity<List<Classroom>> searchClassroom(@RequestBody SearchClassroomDTO dto) throws ClassroomExistsException {
        List<Classroom> classrooms =  service.searchClassroom(dto);
        return ResponseEntity.status(HttpStatus.OK).body(classrooms);

    }


    @ExceptionHandler(ClassroomExistsException.class)
    public  ResponseEntity<String> HandleClassroomDoesNotExist(ClassroomExistsException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

}
