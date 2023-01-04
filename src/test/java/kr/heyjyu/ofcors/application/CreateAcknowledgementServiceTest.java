package kr.heyjyu.ofcors.application;

import kr.heyjyu.ofcors.dtos.AcknowledgementRequestDto;
import kr.heyjyu.ofcors.models.Acknowledgement;
import kr.heyjyu.ofcors.models.Answer;
import kr.heyjyu.ofcors.models.AnswerId;
import kr.heyjyu.ofcors.models.Body;
import kr.heyjyu.ofcors.models.Message;
import kr.heyjyu.ofcors.models.Points;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.QuestionId;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.AcknowledgementRepository;
import kr.heyjyu.ofcors.repositories.AnswerRepository;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreateAcknowledgementServiceTest {
    private AcknowledgementRepository acknowledgementRepository;
    private UserRepository userRepository;
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;
    private CreateAcknowledgementService createAcknowledgementService;

    @BeforeEach
    void setup() {
        acknowledgementRepository = mock(AcknowledgementRepository.class);
        userRepository = mock(UserRepository.class);
        questionRepository = mock(QuestionRepository.class);
        answerRepository = mock(AnswerRepository.class);
        createAcknowledgementService = new CreateAcknowledgementService(acknowledgementRepository, userRepository, questionRepository, answerRepository);
    }

    @Test
    void create() {
        given(questionRepository.findById(1L))
                .willReturn(Optional.of(Question.fake()));

        given(answerRepository.findById(1L))
                .willReturn(Optional.of(Answer.fake()));

        given(userRepository.findById(any()))
                .willReturn(Optional.of(User.fake()));

        Long userId = 1L;
        AnswerId answerId = new AnswerId(1L);
        QuestionId questionId = new QuestionId(1L);
        Points points = new Points(30L);
        Message message = new Message("감사합니다");

        Acknowledgement acknowledgement = createAcknowledgementService.create(userId, answerId, questionId, points, message);

        assertThat(acknowledgement).isNotNull();
        verify(acknowledgementRepository).save(any());
    }
}
