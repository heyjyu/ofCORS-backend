package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.exceptions.AnswerNotFound;
import kr.heyjyu.ofcors.exceptions.InvalidUser;
import kr.heyjyu.ofcors.exceptions.QuestionNotFound;
import kr.heyjyu.ofcors.exceptions.UserNotFound;
import kr.heyjyu.ofcors.models.Acknowledgement;
import kr.heyjyu.ofcors.models.Answer;
import kr.heyjyu.ofcors.models.AnswerId;
import kr.heyjyu.ofcors.models.Message;
import kr.heyjyu.ofcors.models.Points;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.QuestionId;
import kr.heyjyu.ofcors.models.ReceiverId;
import kr.heyjyu.ofcors.models.SenderId;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.AcknowledgementRepository;
import kr.heyjyu.ofcors.repositories.AnswerRepository;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CreateAcknowledgementService {
    private AcknowledgementRepository acknowledgementRepository;
    private UserRepository userRepository;
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;

    public CreateAcknowledgementService(AcknowledgementRepository acknowledgementRepository, UserRepository userRepository, QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.acknowledgementRepository = acknowledgementRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public Acknowledgement create(Long userId, AnswerId answerId, QuestionId questionId, Points points, Message message) {
        Question question = questionRepository.findById(questionId.value())
                .orElseThrow(() -> new QuestionNotFound());

        User sender = userRepository.findById(question.getAuthorId().value())
                .orElseThrow(() -> new UserNotFound(question.getAuthorId().value()));

        Answer answer = answerRepository.findById(answerId.value())
                .orElseThrow(() -> new AnswerNotFound(answerId.value()));

        User receiver = userRepository.findById(answer.getAuthorId().value())
                .orElseThrow(() -> new UserNotFound(answer.getAuthorId().value()));

        if (question.getAuthorId().value() != userId) {
            throw new InvalidUser();
        }

        receiver.receive(question.getPoints());
        sender.transfer(receiver, points);

        SenderId senderId = new SenderId(userId);
        ReceiverId receiverId = new ReceiverId(answer.getAuthorId().value());

        Acknowledgement acknowledgement = new Acknowledgement(senderId, receiverId, points, message);

        acknowledgementRepository.save(acknowledgement);

        return acknowledgement;
    }
}
