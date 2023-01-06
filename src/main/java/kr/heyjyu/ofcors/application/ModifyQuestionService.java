package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.dtos.QuestionDto;
import kr.heyjyu.ofcors.exceptions.InvalidUser;
import kr.heyjyu.ofcors.exceptions.QuestionNotFound;
import kr.heyjyu.ofcors.exceptions.UnmatchedQuestionId;
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
public class ModifyQuestionService {
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;

    public ModifyQuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public Question modify(Long userId, QuestionId questionId, QuestionDto questionDto) {
        Question question = questionRepository.findById(questionId.value())
                .orElseThrow(() -> new QuestionNotFound());
        List<Answer> answers = answerRepository.findAllByQuestionId(questionId);

        if (!question.isAuthor(userId)) {
            throw new InvalidUser();
        }

        if (!questionId.equals(new QuestionId(questionDto.getId()))) {
            throw new UnmatchedQuestionId();
        }

        if (answers.size() > 0) {
            throw new UnmodifiableQuestion();
        }

        question.modify(questionDto);

        return question;
    }
}
