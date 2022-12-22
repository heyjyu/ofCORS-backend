package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class GetTopQuestionsService {
    private QuestionRepository questionRepository;

    public GetTopQuestionsService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getTopQuestions() {
        return questionRepository.findAllOrderByLikesCount();
    }
}
