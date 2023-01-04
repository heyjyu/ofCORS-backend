package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.dtos.AuthorDto;
import kr.heyjyu.ofcors.dtos.QuestionDto;
import kr.heyjyu.ofcors.exceptions.QuestionNotFound;
import kr.heyjyu.ofcors.exceptions.UserNotFound;
import kr.heyjyu.ofcors.models.LikeUserId;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.Tag;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Transactional
public class GetQuestionService {
    private QuestionRepository questionRepository;
    private UserRepository userRepository;

    public GetQuestionService(QuestionRepository questionRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    public QuestionDto getQuestion(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFound());

        User author = userRepository.findById(question.getAuthorId().value())
                .orElseThrow(() -> new UserNotFound(question.getAuthorId().value()));

        return new QuestionDto(
                question.getId(),
                new AuthorDto(
                        author.getId(),
                        author.getDisplayName().value(),
                        author.getImageUrl().value()
                ),
                question.getStatus().value(),
                question.getTitle().value(),
                question.getBody().value(),
                question.getTags().stream().map(Tag::toDto).collect(Collectors.toSet()),
                question.getPoints().value(),
                question.getLikeUserIds().stream().map(LikeUserId::toDto).collect(Collectors.toSet()),
                question.getHits().value(),
                question.getCreatedAt(),
                question.getUpdatedAt()
        );
    }
}
