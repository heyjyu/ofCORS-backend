package kr.heyjyu.ofcors.controllers;

import kr.heyjyu.ofcors.application.CreateQuestionService;
import kr.heyjyu.ofcors.application.GetQuestionService;
import kr.heyjyu.ofcors.application.GetQuestionsService;
import kr.heyjyu.ofcors.dtos.QuestionCreationDto;
import kr.heyjyu.ofcors.dtos.QuestionDto;
import kr.heyjyu.ofcors.dtos.QuestionRequestDto;
import kr.heyjyu.ofcors.dtos.QuestionsDto;
import kr.heyjyu.ofcors.models.Body;
import kr.heyjyu.ofcors.models.Points;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.Tag;
import kr.heyjyu.ofcors.models.Title;
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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("questions")
public class QuestionController {
    private GetQuestionsService getQuestionsService;
    private GetQuestionService getQuestionService;
    private CreateQuestionService createQuestionService;

    public QuestionController(GetQuestionsService getQuestionsService, GetQuestionService getQuestionService, CreateQuestionService createQuestionService) {
        this.getQuestionsService = getQuestionsService;
        this.getQuestionService = getQuestionService;
        this.createQuestionService = createQuestionService;
    }

    @GetMapping
    public QuestionsDto list(@RequestParam(required = false, defaultValue = "") String sort,
                                  @RequestParam(required = false, defaultValue = "") String period,
                                  @RequestParam(required = false, defaultValue = "open") String status,
                                  @RequestParam(required = false, defaultValue = "") String keyword,
                                  @RequestParam(required = false, defaultValue = "20") Integer size) {
        return new QuestionsDto(getQuestionsService.getQuestions(sort, period, status, keyword, size));
    }

    @GetMapping("{id}")
    public QuestionDto detail(@PathVariable Long id) {
        return getQuestionService.getQuestion(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionCreationDto create(@RequestAttribute Long userId, @RequestBody QuestionRequestDto questionRequestDto) {
        Title title = new Title(questionRequestDto.getTitle());
        Body body = new Body(questionRequestDto.getBody());

        Optional<Set<String>> tagsOptional = Optional.ofNullable(questionRequestDto.getTags());
        Set<Tag> tags = tagsOptional.isPresent()
                ? tagsOptional.get().stream().map(tag -> new Tag(tag))
                .collect(Collectors.toSet())
                : new HashSet<>();

        Optional<Long> pointsOptional = Optional.ofNullable(questionRequestDto.getPoints());
        Points points = pointsOptional.isPresent()
                ? new Points(questionRequestDto.getPoints())
                : new Points(0L);

        Question question = createQuestionService.create(userId, title, body, tags, points);

        return question.toCreationDto();
    }
}
