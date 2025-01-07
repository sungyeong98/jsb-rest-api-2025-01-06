package com.ll.sbbrestapi20250106.domain.question;

import com.ll.sbbrestapi20250106.global.rsData.RsData;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
            String subject,
            String content
    ) {}


    @PutMapping("/{id}")
    @Transactional
    public RsData modifyQuestion(@PathVariable Long id,
                                              @RequestBody QuestionModifyReqBody reqBody) {
        Question question = questionService.findById(id).get();

        questionService.modify(question, reqBody.subject, reqBody.content);

        return new RsData(
                "200-1",
                "%d번 글이 수정되었습니다.".formatted(id)
        );
    }

}
