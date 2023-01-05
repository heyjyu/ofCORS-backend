package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.dtos.LikeUserIdsDto;
import kr.heyjyu.ofcors.exceptions.AnswerNotFound;
import kr.heyjyu.ofcors.exceptions.QuestionNotFound;
import kr.heyjyu.ofcors.models.Answer;
import kr.heyjyu.ofcors.models.AnswerId;
import kr.heyjyu.ofcors.models.LikeUserId;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.QuestionId;
import kr.heyjyu.ofcors.repositories.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Transactional
@Service
public class ToggleAnswerLikeService {
    private AnswerRepository answerRepository;

    public ToggleAnswerLikeService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public LikeUserIdsDto toggleLike(AnswerId answerId, LikeUserId likeUserId) {
        Answer answer = answerRepository.findById(answerId.value())
                .orElseThrow(() -> new AnswerNotFound(answerId));

        answer.toggleLike(likeUserId);

        return new LikeUserIdsDto(answer.getLikeUserIds().stream()
                .map(id -> id.toDto())
                .collect(Collectors.toSet()));
    }
}
