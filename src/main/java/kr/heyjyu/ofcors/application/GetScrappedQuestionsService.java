package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.dtos.AuthorDto;
import kr.heyjyu.ofcors.dtos.QuestionDto;
import kr.heyjyu.ofcors.exceptions.UserNotFound;
import kr.heyjyu.ofcors.models.AnswerId;
import kr.heyjyu.ofcors.models.LikeUserId;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.ScrapUserId;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class GetScrappedQuestionsService {
    private QuestionRepository questionRepository;

    private UserRepository userRepository;

    public GetScrappedQuestionsService(QuestionRepository questionRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    public List<QuestionDto> getQuestions(Long userId) {
        // TODO: get page from request
        Integer page = 1;

        Integer size = 30;

        Sort sortBy = Sort.by("createdAt").descending();

        Pageable pageable = PageRequest.of(page - 1, size, sortBy);

        Specification<Question> specification = Specification.where(QuestionSpecification.equalScrapUserId(new ScrapUserId(userId)));

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
                                    question.getScrapUserIds().stream().map(ScrapUserId::toDto).collect(Collectors.toSet()),
                                    selectedAnswerId,
                                    question.getHits().value(),
                                    question.getCreatedAt(),
                                    question.getUpdatedAt()
                            );
                        }
                ).collect(Collectors.toList());
    }
}
