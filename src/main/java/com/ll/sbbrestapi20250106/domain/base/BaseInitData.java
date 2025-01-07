package com.ll.sbbrestapi20250106.domain.base;

import com.ll.sbbrestapi20250106.domain.question.Question;
import com.ll.sbbrestapi20250106.domain.question.QuestionService;
import com.ll.sbbrestapi20250106.domain.user.User;
import com.ll.sbbrestapi20250106.domain.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {

    private final QuestionService questionService;
    private final UserService userService;

    @Autowired
    @Lazy
    private BaseInitData self;

    @Bean
    public ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.work1();
            self.work2();
        };
    }

    @Transactional
    public void work1() {
        if (userService.count() > 0) return;

        User admin = userService.join("admin", "123", "admin", "admin@admin.com");
        User user1 = userService.join("user1", "123", "user1", "user1@user.com");
        User user2 = userService.join("user2", "123", "user2", "user2@user.com");
        User user3 = userService.join("user3", "123", "user3", "user3@user.com");
    }

    @Transactional
    public void work2() {
        if (questionService.count() > 0) return;

        Question q1 = questionService.write("test1", "1111111");
        Question q2 = questionService.write("test2", "2222222");
        Question q3 = questionService.write("test3", "3333333");

    }

}
