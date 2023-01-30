package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.exceptions.QuestionNotFound;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.QuestionId;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CancelScrapQuestionService {
    private QuestionRepository questionRepository;

    public CancelScrapQuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question cancelScrap(Long userId, QuestionId questionId) {
        Question question = questionRepository.findById(questionId.value())
                .orElseThrow(() -> new QuestionNotFound());

        question.removeScrappedUser(userId);

        return question;
    }
}
