package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.dtos.AuthorDto;
import kr.heyjyu.ofcors.dtos.QuestionDto;
import kr.heyjyu.ofcors.exceptions.UserNotFound;
import kr.heyjyu.ofcors.models.AnswerId;
import kr.heyjyu.ofcors.models.AuthorId;
import kr.heyjyu.ofcors.models.LikeUserId;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.QuestionStatus;
import kr.heyjyu.ofcors.models.Tag;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import kr.heyjyu.ofcors.repositories.UserRepository;
import kr.heyjyu.ofcors.specifications.QuestionSpecification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class GetQuestionsService {
    private QuestionRepository questionRepository;
    private UserRepository userRepository;

    public GetQuestionsService(QuestionRepository questionRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    public List<QuestionDto> getQuestions(String sort, String period, String status, String keyword, Integer size) {
        // TODO: get page from request
        Integer page = 1;

        Sort sortBy = Sort.by("createdAt").descending();

        if (sort.equals("like")) {
            sortBy = Sort.by("countOfLikes").descending();
        }

        if (sort.equals("points")) {
            sortBy = Sort.by("points").descending();
        }

        Pageable pageable = PageRequest.of(page - 1, size, sortBy);

        Specification<Question> specification = Specification.where(QuestionSpecification.equalStatus(QuestionStatus.of(status)));

        if (!period.equals("")) {
            specification = specification.and(QuestionSpecification.inPeriod(period));
        }

        if (!keyword.equals("")) {
            specification = specification.and(QuestionSpecification.likeTitleOrBody(keyword));
        }

        return questionRepository.findAll(specification, pageable)
                .stream()
                .map(question -> {
                            User author = userRepository.findById(question.getAuthorId().value())
                                    .orElseThrow(() -> new UserNotFound(question.getAuthorId().value()));

                            Optional<AnswerId> selectedAnswerIdOptional = Optional.ofNullable(question.getSelectedAnswerId());
                            Long selectedAnswerId = selectedAnswerIdOptional.isPresent()
                                    ? selectedAnswerIdOptional.get().value()
                                    : null;

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
                                    selectedAnswerId,
                                    question.getHits().value(),
                                    question.getCreatedAt(),
                                    question.getUpdatedAt()
                            );
                        }
                ).collect(Collectors.toList());
    }

    public List<QuestionDto> getQuestions(String sort, Long userId, Integer size) {
        // TODO: get page from request
        Integer page = 1;

        Sort sortBy = Sort.by("createdAt").descending();

        if (sort.equals("like")) {
            sortBy = Sort.by("countOfLikes").descending();
        }

        if (sort.equals("points")) {
            sortBy = Sort.by("points").descending();
        }

        Pageable pageable = PageRequest.of(page - 1, size, sortBy);

        Specification<Question> specification = Specification.where(QuestionSpecification.equalAuthorId(new AuthorId(userId)));

        return questionRepository.findAll(specification, pageable)
                .stream()
                .map(question -> {
                            User author = userRepository.findById(question.getAuthorId().value())
                                    .orElseThrow(() -> new UserNotFound(question.getAuthorId().value()));

                            Optional<AnswerId> selectedAnswerIdOptional = Optional.ofNullable(question.getSelectedAnswerId());
                            Long selectedAnswerId = selectedAnswerIdOptional.isPresent()
                                    ? selectedAnswerIdOptional.get().value()
                                    : null;

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
                                    selectedAnswerId,
                                    question.getHits().value(),
                                    question.getCreatedAt(),
                                    question.getUpdatedAt()
                            );
                        }
                ).collect(Collectors.toList());
    }
}
