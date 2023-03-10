package kr.heyjyu.ofcors.controllers;

import jakarta.servlet.http.HttpServletRequest;
import kr.heyjyu.ofcors.application.AdoptAnswerService;
import kr.heyjyu.ofcors.application.CreateQuestionService;
import kr.heyjyu.ofcors.application.DeleteQuestionService;
import kr.heyjyu.ofcors.application.GetQuestionService;
import kr.heyjyu.ofcors.application.GetQuestionsService;
import kr.heyjyu.ofcors.application.ModifyQuestionService;
import kr.heyjyu.ofcors.dtos.AdoptRequestDto;
import kr.heyjyu.ofcors.dtos.QuestionCreationDto;
import kr.heyjyu.ofcors.dtos.QuestionDto;
import kr.heyjyu.ofcors.dtos.QuestionModificationDto;
import kr.heyjyu.ofcors.dtos.QuestionRequestDto;
import kr.heyjyu.ofcors.dtos.QuestionsDto;
import kr.heyjyu.ofcors.models.AnswerId;
import kr.heyjyu.ofcors.models.Body;
import kr.heyjyu.ofcors.models.Points;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.QuestionId;
import kr.heyjyu.ofcors.models.Tag;
import kr.heyjyu.ofcors.models.Title;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    private AdoptAnswerService adoptAnswerService;
    private ModifyQuestionService modifyQuestionService;
    private DeleteQuestionService deleteQuestionService;

    public QuestionController(GetQuestionsService getQuestionsService, GetQuestionService getQuestionService, CreateQuestionService createQuestionService, AdoptAnswerService adoptAnswerService, ModifyQuestionService modifyQuestionService, DeleteQuestionService deleteQuestionService) {
        this.getQuestionsService = getQuestionsService;
        this.getQuestionService = getQuestionService;
        this.createQuestionService = createQuestionService;
        this.adoptAnswerService = adoptAnswerService;
        this.modifyQuestionService = modifyQuestionService;
        this.deleteQuestionService = deleteQuestionService;
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
    public QuestionDto detail(HttpServletRequest request, @PathVariable Long id) {
        String ip = request.getRemoteAddr();

        return getQuestionService.getQuestion(id, ip);
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

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adopt(@RequestAttribute Long userId, @PathVariable Long id, @RequestBody AdoptRequestDto adoptRequestDto) {
        QuestionId questionId = new QuestionId(id);
        AnswerId answerId = new AnswerId(adoptRequestDto.getAnswerId());

        adoptAnswerService.adopt(userId, questionId, answerId);
    }

    @PutMapping("{id}")
    public QuestionModificationDto modify(@RequestAttribute Long userId, @PathVariable Long id, @RequestBody QuestionDto questionDto) {
        QuestionId questionId = new QuestionId(id);

        Question question = modifyQuestionService.modify(userId, questionId, questionDto);

        return question.toModificationDto();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestAttribute Long userId, @PathVariable Long id) {
        QuestionId questionId = new QuestionId(id);

        deleteQuestionService.delete(userId, questionId);
    }
}
