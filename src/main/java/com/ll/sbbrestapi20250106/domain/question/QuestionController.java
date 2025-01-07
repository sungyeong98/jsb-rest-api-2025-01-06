package com.ll.sbbrestapi20250106.domain.question;

import com.ll.sbbrestapi20250106.global.rsData.RsData;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/question_list")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    public List<Question> getList() {
        return questionService.findAll();
    }

    @GetMapping("/{id}")
    public Question getDetail(@PathVariable Long id) {
        return questionService.findById(id).get();
    }

    @DeleteMapping("/{id}")
    public RsData deleteQuestion(@PathVariable Long id) {
        Question question = questionService.findById(id).get();
        questionService.delete(question);

        return new RsData(
                "200-1",
                "%번 글이 삭제되었습니다.".formatted(id)
        );
    }

    record QuestionModifyReqBody (
            @NotBlank
            @Length(min = 3)
            String subject,
            @NotBlank
            @Length(min = 3)
            String content
    ) {}


    @PutMapping("/{id}")
    @Transactional
    public RsData modifyQuestion(@PathVariable Long id,
                                 @RequestBody @Valid QuestionModifyReqBody reqBody) {
        Question question = questionService.findById(id).get();

        questionService.modify(question, reqBody.subject, reqBody.content);

        return new RsData(
                "200-1",
                "%d번 글이 수정되었습니다.".formatted(id)
        );
    }

    record QuestionCreateReqBody (
            @NotBlank
            @Length(min = 3)
            String subject,
            @NotBlank
            @Length(min = 3)
            String content
    ) {}

    @PostMapping
    public RsData createQuestion(@RequestBody @Valid QuestionCreateReqBody reqBody) {
        Question question = questionService.write(reqBody.subject, reqBody.content);

        return new RsData(
                "200-1",
                "%d번 글이 작성되었습니다.".formatted(question.getId())
        );
    }

}
