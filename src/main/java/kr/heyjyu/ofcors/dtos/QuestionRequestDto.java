package kr.heyjyu.ofcors.dtos;

import java.util.List;
import java.util.Set;

public class QuestionRequestDto {
    private String title;
    private String body;
    private Set<String> tags;
    private Long points;

    public QuestionRequestDto() {
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Set<String> getTags() {
        return tags;
    }

    public Long getPoints() {
        return points;
    }
}
