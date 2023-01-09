package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.exceptions.InvalidUser;
import kr.heyjyu.ofcors.exceptions.QuestionNotFound;
import kr.heyjyu.ofcors.exceptions.UnmodifiableQuestion;
import kr.heyjyu.ofcors.models.Answer;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.QuestionId;
import kr.heyjyu.ofcors.repositories.AnswerRepository;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DeleteQuestionService {
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;

    public DeleteQuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public void delete(Long userId, QuestionId questionId) {
        Question question = questionRepository.findById(questionId.value())
                .orElseThrow(() -> new QuestionNotFound());
        List<Answer> answers = answerRepository.findAllByQuestionId(questionId);

        if (!question.isAuthor(userId)) {
            throw new InvalidUser();
        }

        if (answers.size() > 0) {
            throw new UnmodifiableQuestion();
        }

        questionRepository.delete(question);
    }
}
