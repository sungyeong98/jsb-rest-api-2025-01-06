package com.ll.sbbrestapi20250106.domain.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public long count() {
        return questionRepository.count();
    }

    public Question write(String subject, String content) {
        Question question = Question.builder()
                .subject(subject)
                .content(content)
                .build();

        return questionRepository.save(question);
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Optional<Question> findById(long id) {
        return questionRepository.findById(id);
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }

    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
    }

}
