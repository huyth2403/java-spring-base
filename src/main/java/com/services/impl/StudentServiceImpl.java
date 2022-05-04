package com.services.impl;

import com.entities.db1.Student;
import com.repositories.db1.StudentRepository;
import com.services.AuthService;
import com.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class StudentServiceImpl implements StudentService {

//    implicit transaction: auto commit
//    explicit transaction: nếu xảy ra lỗi thì sẽ rollback
//    các thao tác CRUD nếu không đánh dấu hàm với chú thích @Transactional mặc định sẽ tạo ra 1 implicit transaction, thuộc tính rollbackFor mặc định sẽ chỉ rollback với RuntimeException hoặc Error
//    ngược lại, nếu đánh dấu hàm với chú thích @Transactional sẽ tham gia chung 1 transaction được đang đc kích hoạt
// *** nếu 2 method đều có chung 1 class thì 2 method sẽ luôn có chung 1 transaction

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
        throw new RuntimeException();
//        return "OK";
    }

}
