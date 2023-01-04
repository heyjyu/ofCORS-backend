package kr.heyjyu.ofcors.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import kr.heyjyu.ofcors.dtos.AcknowledgementCreationDto;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class Acknowledgement {
    @Id
    @GeneratedValue
    private Long id;

    private SenderId senderId;

    private ReceiverId receiverId;

    private Points points;

    private Message message;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Acknowledgement() {
    }

    public Acknowledgement(SenderId senderId, ReceiverId receiverId, Points points, Message message) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.points = points;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public SenderId getSenderId() {
        return senderId;
    }

    public ReceiverId getReceiverId() {
        return receiverId;
    }

    public Points getPoints() {
        return points;
    }

    public Message getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public AcknowledgementCreationDto toCreationDto() {
        return new AcknowledgementCreationDto(id);
    }

    public static Acknowledgement fake() {
        SenderId senderId = new SenderId(1L);
        ReceiverId receiverId = new ReceiverId(2L);
        Points points = new Points(10L);
        Message message = new Message("감사합니다");

        return new Acknowledgement(senderId, receiverId, points, message);
    }
}
