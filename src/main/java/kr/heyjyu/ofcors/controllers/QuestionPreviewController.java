package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.GetQuestionPreviewsService;
import kr.heyjyu.ofcors.dtos.QuestionPreviewsDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("question-previews")
public class QuestionPreviewController {
    private GetQuestionPreviewsService getQuestionPreviewsService;

    public QuestionPreviewController(GetQuestionPreviewsService getQuestionPreviewsService) {
        this.getQuestionPreviewsService = getQuestionPreviewsService;
    }

    @GetMapping
    public QuestionPreviewsDto list(
            @RequestParam(required = false, defaultValue = "") Long userId,
            @RequestParam(required = false, defaultValue = "") String sort) {
        return new QuestionPreviewsDto(getQuestionPreviewsService.getQuestionPreviews(userId, sort));
    }
}
