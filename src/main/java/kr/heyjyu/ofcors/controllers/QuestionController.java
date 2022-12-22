package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.GetTopQuestionsService;
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
    private GetTopQuestionsService getTopQuestionsService;

    public QuestionController(GetTopQuestionsService getTopQuestionsService) {
        this.getTopQuestionsService = getTopQuestionsService;
    }

    @GetMapping
    public QuestionsDto topQuestions(@RequestParam String filter) {
        if (filter.equals("top")) {
            return new QuestionsDto(getTopQuestionsService.getTopQuestions().stream()
                    .map(Question::toDto)
                    .collect(Collectors.toList()));
        }

        return null;
    }
}
