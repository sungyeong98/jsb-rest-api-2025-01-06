package com.ll.sbbrestapi20250106.domain.question.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.sbbrestapi20250106.domain.question.Question;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
public class QuestionDto {
    private Long id;
    @JsonIgnore
    private LocalDateTime createDate;
    @JsonIgnore
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }
}
