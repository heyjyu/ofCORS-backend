package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.GetAnswerPreviewsService;
import kr.heyjyu.ofcors.dtos.AnswerPreviewsDto;
import kr.heyjyu.ofcors.dtos.AnswersDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("answer-previews")
public class AnswerPreviewController {
    private GetAnswerPreviewsService getAnswerPreviewsService;

    public AnswerPreviewController(GetAnswerPreviewsService getAnswerPreviewsService) {
        this.getAnswerPreviewsService = getAnswerPreviewsService;
    }

    @GetMapping
    public AnswerPreviewsDto list(@RequestParam(required = false, defaultValue = "") Long userId) {
        return new AnswerPreviewsDto(getAnswerPreviewsService.getAnswerPreviews(userId));
    }
}
