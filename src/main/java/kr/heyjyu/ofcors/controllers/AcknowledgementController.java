package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.CreateAcknowledgementService;
import kr.heyjyu.ofcors.dtos.AcknowledgementCreationDto;
import kr.heyjyu.ofcors.dtos.AcknowledgementRequestDto;
import kr.heyjyu.ofcors.models.Acknowledgement;
import kr.heyjyu.ofcors.models.AnswerId;
import kr.heyjyu.ofcors.models.Message;
import kr.heyjyu.ofcors.models.Points;
import kr.heyjyu.ofcors.models.QuestionId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("acknowledgements")
public class AcknowledgementController {
    private CreateAcknowledgementService createAcknowledgementService;

    public AcknowledgementController(CreateAcknowledgementService createAcknowledgementService) {
        this.createAcknowledgementService = createAcknowledgementService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AcknowledgementCreationDto create(@RequestAttribute Long userId, @RequestBody AcknowledgementRequestDto acknowledgementRequestDto) {
        AnswerId answerId = new AnswerId(acknowledgementRequestDto.getAnswerId());
        QuestionId questionId = new QuestionId(acknowledgementRequestDto.getQuestionId());
        Points points = new Points(acknowledgementRequestDto.getPoints());
        Message message = new Message(acknowledgementRequestDto.getMessage());

        Acknowledgement acknowledgement = createAcknowledgementService.create(userId, answerId, questionId, points, message);

        return acknowledgement.toCreationDto();
    }
}
