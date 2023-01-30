package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.CancelScrapQuestionService;
import kr.heyjyu.ofcors.application.ScrapQuestionService;
import kr.heyjyu.ofcors.dtos.QuestionScrapResultDto;
import kr.heyjyu.ofcors.dtos.ScrapRequestDto;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.QuestionId;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScrapController {
    private ScrapQuestionService scrapQuestionService;
    private CancelScrapQuestionService cancelScrapQuestionService;

    public ScrapController(ScrapQuestionService scrapQuestionService, CancelScrapQuestionService cancelScrapQuestionService) {
        this.scrapQuestionService = scrapQuestionService;
        this.cancelScrapQuestionService = cancelScrapQuestionService;
    }

    @PatchMapping("questions/{id}/scrapUserIds")
    public QuestionScrapResultDto scrap(@RequestAttribute Long userId, @PathVariable Long id, @RequestBody ScrapRequestDto scrapRequestDto) {
        QuestionId questionId = new QuestionId(id);

        System.out.println(scrapRequestDto.isScrapped());

        if (scrapRequestDto.isScrapped()) {
            Question question = scrapQuestionService.scrap(userId, questionId);

            return question.toScrapResultDto();
        }

        Question question = cancelScrapQuestionService.cancelScrap(userId, questionId);

        return question.toScrapResultDto();
    }
}
