package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.exceptions.UserNotFound;
import kr.heyjyu.ofcors.models.AuthorId;
import kr.heyjyu.ofcors.models.Body;
import kr.heyjyu.ofcors.models.Points;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.Tag;
import kr.heyjyu.ofcors.models.Title;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Transactional
public class CreateQuestionService {
    private QuestionRepository questionRepository;
    private UserRepository userRepository;

    public CreateQuestionService(QuestionRepository questionRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    public Question create(Long userId, Title title, Body body, Set<Tag> tags, Points points) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound(userId));

        Question question = new Question(new AuthorId(userId), title, body, tags, points);

        user.ask(question);

        questionRepository.save(question);

        return question;
    }
}
