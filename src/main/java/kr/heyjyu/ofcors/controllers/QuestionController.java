package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.GetQuestionsService;
import kr.heyjyu.ofcors.dtos.QuestionsDto;
import kr.heyjyu.ofcors.models.Question;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("questions")
public class QuestionController {
    private GetQuestionsService getQuestionsService;

    public QuestionController(GetQuestionsService getQuestionsService) {
        this.getQuestionsService = getQuestionsService;
    }

    @GetMapping
    public QuestionsDto questions(@RequestParam String sort,
                                  @RequestParam String period,
                                  @RequestParam String status,
                                  @RequestParam Integer size) {
        return new QuestionsDto(getQuestionsService.getQuestions(sort, period, status, size).stream()
                .map(Question::toDto)
                .collect(Collectors.toList()));
    }
}
