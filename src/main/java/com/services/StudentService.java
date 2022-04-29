package com.services;

import com.entities.Student;
import com.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public String save(Student student) throws Exception {
        save1();
        Student std1 = studentRepository.findById(1).orElse(null);
        std1.setName("save");
        studentRepository.save(std1);
        throw new RuntimeException();
//        return "OK";
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save1() {
        Student std1 = studentRepository.findById(1).orElse(null);
        std1.setName("save1");
        studentRepository.save(std1);
    }

}
