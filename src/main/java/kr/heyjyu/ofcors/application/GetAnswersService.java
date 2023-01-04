package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.dtos.AnswerDto;
import kr.heyjyu.ofcors.dtos.AuthorDto;
import kr.heyjyu.ofcors.exceptions.UserNotFound;
import kr.heyjyu.ofcors.models.Answer;
import kr.heyjyu.ofcors.models.LikeUserId;
import kr.heyjyu.ofcors.models.QuestionId;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.AnswerRepository;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class GetAnswersService {
    private AnswerRepository answerRepository;
    private UserRepository userRepository;

    public GetAnswersService(AnswerRepository answerRepository, UserRepository userRepository) {
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
    }

    public List<AnswerDto> getAnswers(QuestionId questionId) {
        List<Answer> answers = answerRepository.findAllByQuestionId(questionId);

        return answers
                .stream()
                .map(answer -> {
                            User author = userRepository.findById(answer.getAuthorId().value())
                                    .orElseThrow(() -> new UserNotFound(answer.getAuthorId().value()));

                            return new AnswerDto(
                                    answer.getId(),
                                    answer.getQuestionId().value(),
                                    new AuthorDto(
                                            author.getId(),
                                            author.getDisplayName().value(),
                                            author.getImageUrl().value()
                                    ),
                                    answer.getBody().value(),
                                    answer.getLikeUserIds().stream().map(LikeUserId::toDto).collect(Collectors.toSet()),
                                    answer.getCreatedAt(),
                                    answer.getUpdatedAt()
                            );
                        }
                ).collect(Collectors.toList());
    }
}
