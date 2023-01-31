package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.dtos.AnswerPreviewDto;
import kr.heyjyu.ofcors.dtos.AuthorDto;
import kr.heyjyu.ofcors.dtos.QuestionDto;
import kr.heyjyu.ofcors.exceptions.QuestionNotFound;
import kr.heyjyu.ofcors.exceptions.UserNotFound;
import kr.heyjyu.ofcors.models.Answer;
import kr.heyjyu.ofcors.models.AnswerId;
import kr.heyjyu.ofcors.models.AuthorId;
import kr.heyjyu.ofcors.models.LikeUserId;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.ScrapUserId;
import kr.heyjyu.ofcors.models.Tag;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.AnswerRepository;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class GetAnswerPreviewsService {
    private AnswerRepository answerRepository;
    private UserRepository userRepository;
    private QuestionRepository questionRepository;

    public GetAnswerPreviewsService(AnswerRepository answerRepository, UserRepository userRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    public List<AnswerPreviewDto> getAnswerPreviews(Long userId, String sort) {
        // TODO: get from request
        Integer page = 1;
        Integer size = 20;

        Sort sortBy = Sort.by("countOfLikes").descending();

        if (sort.equals("createdAt")) {
            sortBy = Sort.by("createdAt").descending();
        }

        Pageable pageable = PageRequest.of(page - 1, size, sortBy);

        List<Answer> answers = answerRepository.findAllByAuthorId(new AuthorId(userId), pageable);

        return answers
                .stream()
                .map(answer -> {
                            User author = userRepository.findById(answer.getAuthorId().value())
                                    .orElseThrow(() -> new UserNotFound(answer.getAuthorId().value()));

                            Question question = questionRepository.findById(answer.getQuestionId().value())
                                    .orElseThrow(() -> new QuestionNotFound());

                            Optional<AnswerId> selectedAnswerIdOptional = Optional.ofNullable(question.getSelectedAnswerId());
                            Long selectedAnswerId = selectedAnswerIdOptional.isPresent()
                                    ? selectedAnswerIdOptional.get().value()
                                    : null;

                            return new AnswerPreviewDto(
                                    answer.getId(),
                                    new QuestionDto(
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
                                            question.getScrapUserIds().stream().map(ScrapUserId::toDto).collect(Collectors.toSet()),
                                            selectedAnswerId,
                                            question.getHits().value(),
                                            question.getCreatedAt(),
                                            question.getUpdatedAt()
                                    ),
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
