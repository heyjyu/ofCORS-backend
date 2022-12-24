package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.models.Question;
import kr.heyjyu.ofcors.models.QuestionStatus;
import kr.heyjyu.ofcors.repositories.QuestionRepository;
import kr.heyjyu.ofcors.specifications.QuestionSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class GetQuestionsService {
    private QuestionRepository questionRepository;

    public GetQuestionsService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Page<Question> getQuestions(String sort, String period, String status, String keyword, Integer size) {
        // TODO: get page from request
        Integer page = 1;

        Sort sortBy = Sort.by("createdAt").descending();

        if (sort.equals("like")) {
            sortBy = Sort.by("countOfLikes").descending();
        }

        if (sort.equals("points")) {
            sortBy = Sort.by("points").descending();
        }

        Pageable pageable = PageRequest.of(page - 1, size, sortBy);

        Specification<Question> specification = Specification.where(QuestionSpecification.equalStatus(QuestionStatus.of(status)));

        if (!period.equals("")) {
            specification = specification.and(QuestionSpecification.inPeriod(period));
        }

        if (!keyword.equals("")) {
            specification = specification.and(QuestionSpecification.likeTitleOrBody(keyword));
        }

        return questionRepository.findAll(specification, pageable);
    }
}
