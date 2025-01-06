package com.ll.sbbrestapi20250106.domain.base;

import com.ll.sbbrestapi20250106.domain.question.Question;
import com.ll.sbbrestapi20250106.domain.question.QuestionService;
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

    @Autowired
    @Lazy
    private BaseInitData self;

    @Bean
    public ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    public void work1() {
        if (questionService.count() > 0) return;

        Question q1 = questionService.write("test1", "1111111");
        Question q2 = questionService.write("test2", "2222222");
        Question q3 = questionService.write("test3", "3333333");

    }

}
