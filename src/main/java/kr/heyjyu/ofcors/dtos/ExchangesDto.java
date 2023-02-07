package kr.heyjyu.ofcors.dtos;

import java.util.List;

public class ExchangesDto {
    private List<ExchangeDto> exchanges;

    public ExchangesDto(List<ExchangeDto> exchanges) {
        this.exchanges = exchanges;
    }

    public List<ExchangeDto> getExchanges() {
        return exchanges;
    }
}
