package com.controller;

import com.entities.db1.Student;
import com.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public String save(@RequestBody Student student){
        try {
            return studentService.save(student);
        } catch (Exception e) {
            e.printStackTrace();
            return "Exception";
        }
    }
}
