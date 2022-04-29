package com.services.impl;

import com.entities.Student;
import com.repositories.StudentRepository;
import com.services.AuthService;
import com.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class StudentServiceImpl implements StudentService {

//    implicit transaction: auto commit
//    explicit transaction: nếu xảy ra lỗi thì sẽ rollback
//    các thao tác CRUD nếu không đánh dấu hàm với chú thích @Transactional mặc định sẽ tạo ra 1 implicit transaction
//    ngược lại, nếu đánh dấu hàm với chú thích @Transactional sẽ tham gia chung 1 transaction được đang đc kích hoạt

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AuthService authService;

    @Override
    @Transactional
    public String save(Student student) {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            Student s = new Student();
            s.setName("" + 1);
            studentRepository.save(s);
        }
        System.out.println("total: " + (System.currentTimeMillis() - begin));
        authService.save1();
        throw new RuntimeException();
//        return "OK";
    }

}
