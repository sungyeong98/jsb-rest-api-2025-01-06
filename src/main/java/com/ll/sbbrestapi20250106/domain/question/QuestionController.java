package com.ll.sbbrestapi20250106.domain.question;

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
    public Map<String, Object> deleteQuestion(@PathVariable Long id) {
        Question question = questionService.findById(id).get();
        questionService.delete(question);

        // rsData는 자세한 응답 내용을 담기 위한 존재
        // 이걸 안쓸거면 그냥 문자열만 반환해도 될 듯함 (이걸 꼭 사용해야 되는 건 아닌듯)
        Map<String, Object> rsData = new HashMap<>();
        rsData.put("resultCode", "200-1");
        rsData.put("msg", "%d번 게시물이 삭제되었습니다.".formatted(id));

        return rsData;
    }

}
