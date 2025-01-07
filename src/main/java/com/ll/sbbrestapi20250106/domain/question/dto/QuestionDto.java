package com.ll.sbbrestapi20250106.domain.question.dto;

import com.ll.sbbrestapi20250106.domain.question.Question;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QuestionDto {
    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String subject;
    private String content;

    public QuestionDto(Question question) {
        this.id = question.getId();
        this.createDate = question.getCreateDate();
        this.modifyDate = question.getModifyDate();
        this.subject = question.getSubject();
        this.content = question.getContent();
    }
}
