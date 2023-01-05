package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.ToggleAnswerLikeService;
import kr.heyjyu.ofcors.application.ToggleQuestionLikeService;
import kr.heyjyu.ofcors.dtos.LikeUserIdsDto;
import kr.heyjyu.ofcors.models.AnswerId;
import kr.heyjyu.ofcors.models.LikeUserId;
import kr.heyjyu.ofcors.models.QuestionId;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {
    private ToggleQuestionLikeService toggleQuestionLikeService;
    private ToggleAnswerLikeService toggleAnswerLikeService;

    public LikeController(ToggleQuestionLikeService toggleQuestionLikeService, ToggleAnswerLikeService toggleAnswerLikeService) {
        this.toggleQuestionLikeService = toggleQuestionLikeService;
        this.toggleAnswerLikeService = toggleAnswerLikeService;
    }

    @PatchMapping("questions/{id}/likeUserIds")
    public LikeUserIdsDto toggleLikeQuestion(@RequestAttribute Long userId, @PathVariable Long id) {
        QuestionId questionId = new QuestionId(id);
        LikeUserId likeUserId = new LikeUserId(userId);

        return toggleQuestionLikeService.toggleLike(questionId, likeUserId);
    }

    @PatchMapping("answers/{id}/likeUserIds")
    public LikeUserIdsDto toggleLikeAnswer(@RequestAttribute Long userId, @PathVariable Long id) {
        AnswerId answerId = new AnswerId(id);
        LikeUserId likeUserId = new LikeUserId(userId);

        return toggleAnswerLikeService.toggleLike(answerId, likeUserId);
    }
}
