package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.exceptions.QuestionNotFound;
import kr.heyjyu.ofcors.models.AnswerId;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.QuestionId;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class AdoptAnswerService {
    private QuestionRepository questionRepository;

    public AdoptAnswerService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void adopt(Long userId, QuestionId questionId, AnswerId answerId) {
        Question question = questionRepository.findById(questionId.value())
                .orElseThrow(() -> new QuestionNotFound());

        question.adopt(userId, answerId);
    }
}
