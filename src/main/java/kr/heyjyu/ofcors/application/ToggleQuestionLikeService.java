package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.dtos.LikeUserIdsDto;
import kr.heyjyu.ofcors.exceptions.QuestionNotFound;
import kr.heyjyu.ofcors.exceptions.UserNotFound;
import kr.heyjyu.ofcors.models.LikeUserId;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.QuestionId;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Transactional
@Service
public class ToggleQuestionLikeService {
    private QuestionRepository questionRepository;

    public ToggleQuestionLikeService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public LikeUserIdsDto toggleLike(QuestionId questionId, LikeUserId likeUserId) {
        Question question = questionRepository.findById(questionId.value())
                .orElseThrow(() -> new QuestionNotFound());

        question.toggleLike(likeUserId);

        return new LikeUserIdsDto(question.getLikeUserIds().stream()
                .map(id -> id.toDto())
                .collect(Collectors.toSet()));
    }
}
