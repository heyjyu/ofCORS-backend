package kr.heyjyu.ofcors.dtos;

import kr.heyjyu.ofcors.models.Amount;
import kr.heyjyu.ofcors.models.CardInfo;

import java.time.LocalDateTime;

public class KakaoPayApprovalDto {
    private String partner_order_id;
    private Amount amount;
    private Integer quantity;
    private LocalDateTime approved_at;

    public KakaoPayApprovalDto() {
    }

    public KakaoPayApprovalDto(String partner_order_id, Amount amount, Integer quantity, LocalDateTime approved_at) {
        this.partner_order_id = partner_order_id;
        this.amount = amount;
        this.quantity = quantity;
        this.approved_at = approved_at;
    }

    public String getPartner_order_id() {
        return partner_order_id;
    }

    public Amount getAmount() {
        return amount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public LocalDateTime getApproved_at() {
        return approved_at;
    }
}
