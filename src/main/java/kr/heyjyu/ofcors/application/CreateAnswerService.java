package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.exceptions.AnswerByAuthor;
import kr.heyjyu.ofcors.exceptions.QuestionNotFound;
import kr.heyjyu.ofcors.models.Answer;
import kr.heyjyu.ofcors.models.AuthorId;
import kr.heyjyu.ofcors.models.Body;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.QuestionId;
import kr.heyjyu.ofcors.repositories.AnswerRepository;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CreateAnswerService {
    private AnswerRepository answerRepository;
    private QuestionRepository questionRepository;

    public CreateAnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    public Answer create(Long userId, QuestionId questionId, Body body) {
        Question question = questionRepository.findById(questionId.value())
                .orElseThrow(() -> new QuestionNotFound());

        if (question.getAuthorId().equals(new AuthorId(userId))) {
            throw new AnswerByAuthor();
        }

        Answer answer = new Answer(questionId, body, new AuthorId(userId));

        answerRepository.save(answer);

        return answer;
    }
}
