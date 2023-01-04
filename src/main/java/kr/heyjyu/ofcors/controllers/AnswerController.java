package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.CreateAnswerService;
import kr.heyjyu.ofcors.application.GetAnswerService;
import kr.heyjyu.ofcors.application.GetAnswersService;
import kr.heyjyu.ofcors.dtos.AnswerCreationDto;
import kr.heyjyu.ofcors.dtos.AnswerCreationRequestDto;
import kr.heyjyu.ofcors.dtos.AnswerDto;
import kr.heyjyu.ofcors.dtos.AnswersDto;
import kr.heyjyu.ofcors.models.Answer;
import kr.heyjyu.ofcors.models.Body;
import kr.heyjyu.ofcors.models.QuestionId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("answers")
public class AnswerController {
    private CreateAnswerService createAnswerService;
    private GetAnswersService getAnswersService;
    private GetAnswerService getAnswerService;

    public AnswerController(CreateAnswerService createAnswerService, GetAnswersService getAnswersService, GetAnswerService getAnswerService) {
        this.createAnswerService = createAnswerService;
        this.getAnswersService = getAnswersService;
        this.getAnswerService = getAnswerService;
    }

    @GetMapping
    public AnswersDto list(@RequestParam Long questionId) {
        return new AnswersDto(getAnswersService.getAnswers(new QuestionId(questionId)));
    }

    @GetMapping("{id}")
    public AnswerDto detail(@PathVariable Long id) {
        return getAnswerService.getAnswer(id);
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
