package kr.heyjyu.ofcors.dtos;

import java.util.Set;

public class QuestionScrapResultDto {
    private Set<ScrapUserIdDto> scrapUserIds;

    public QuestionScrapResultDto() {
    }

    public QuestionScrapResultDto(Set<ScrapUserIdDto> scrapUserIds) {
        this.scrapUserIds = scrapUserIds;
    }

    public Set<ScrapUserIdDto> getScrapUserIds() {
        return scrapUserIds;
    }
}
