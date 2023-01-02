package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.CreateAnswerService;
import kr.heyjyu.ofcors.dtos.AnswerCreationDto;
import kr.heyjyu.ofcors.dtos.AnswerCreationRequestDto;
import kr.heyjyu.ofcors.models.Answer;
import kr.heyjyu.ofcors.models.Body;
import kr.heyjyu.ofcors.models.QuestionId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answers")
public class AnswerController {
    private CreateAnswerService createAnswerService;

    public AnswerController(CreateAnswerService createAnswerService) {
        this.createAnswerService = createAnswerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnswerCreationDto create(
            @RequestAttribute Long userId,
            @RequestBody AnswerCreationRequestDto answerCreationRequestDto) {
        Body body = new Body(answerCreationRequestDto.getBody());
        QuestionId questionId = new QuestionId(answerCreationRequestDto.getQuestionId());

        Answer answer = createAnswerService.create(userId, questionId, body);

        return answer.toCreationDto();
    }
}
