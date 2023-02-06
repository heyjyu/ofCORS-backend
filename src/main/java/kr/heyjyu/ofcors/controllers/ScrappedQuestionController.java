package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.GetScrappedQuestionsService;
import kr.heyjyu.ofcors.dtos.QuestionsDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("scrapped-questions")
public class ScrappedQuestionController {
    private GetScrappedQuestionsService getScrappedQuestionsService;

    public ScrappedQuestionController(GetScrappedQuestionsService getScrappedQuestionsService) {
        this.getScrappedQuestionsService = getScrappedQuestionsService;
    }

    @GetMapping
    public QuestionsDto list(@RequestAttribute Long userId) {
        return new QuestionsDto(getScrappedQuestionsService.getQuestions(userId));
    }
}
