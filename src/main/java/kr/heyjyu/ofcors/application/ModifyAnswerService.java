package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.exceptions.AnswerNotFound;
import kr.heyjyu.ofcors.exceptions.InvalidUser;
import kr.heyjyu.ofcors.exceptions.QuestionNotFound;
import kr.heyjyu.ofcors.exceptions.UnmatchedQuestionId;
import kr.heyjyu.ofcors.exceptions.UnmodifiableAnswer;
import kr.heyjyu.ofcors.exceptions.UnmodifiableQuestion;
import kr.heyjyu.ofcors.models.Answer;
import kr.heyjyu.ofcors.models.AnswerId;
import kr.heyjyu.ofcors.models.Body;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.QuestionId;
import kr.heyjyu.ofcors.repositories.AnswerRepository;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class
ModifyAnswerService {
    private AnswerRepository answerRepository;
    private QuestionRepository questionRepository;

    public ModifyAnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    public void modify(Long userId, AnswerId answerId, Body body) {
        Answer answer = answerRepository.findById(answerId.value())
                .orElseThrow(() -> new AnswerNotFound(answerId.value()));
        Question question = questionRepository.findById(answer.getQuestionId().value())
                .orElseThrow(() -> new QuestionNotFound());

        if (!answer.isAuthor(userId)) {
            throw new InvalidUser();
        }

        if (question.isClosed()) {
            throw new UnmodifiableAnswer();
        }

        answer.modify(body);
    }
}
