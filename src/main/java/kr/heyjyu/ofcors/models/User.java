package kr.heyjyu.ofcors.models;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.heyjyu.ofcors.dtos.UserCreationDto;
import kr.heyjyu.ofcors.dtos.UserDto;
import kr.heyjyu.ofcors.exceptions.NotEnoughPoints;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Embedded
    private DisplayName displayName;

    @ElementCollection
    private List<Tag> tags = new ArrayList<>();

    @Embedded
    private About about;

    @Embedded
    private Points points;

    @Embedded
    private ImageUrl imageUrl;

    @Embedded
    private Name name;

    @ElementCollection
    private List<FollowerId> followerIds = new ArrayList<>();

    @ElementCollection
    private List<ScrapId> scrapIds = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public static final Points INITIAL_POINT = new Points(100L);

    public User() {
    }

    public User(DisplayName displayName, Email email) {
        this.displayName = displayName;
        this.email = email;
        this.about = new About("");
        this.imageUrl = new ImageUrl("https://ui-avatars.com/api/?name=" + displayName);
        this.name = new Name("");
    }

    public void changePassword(Password password, PasswordEncoder passwordEncoder) {
        this.password = new Password(password, passwordEncoder);
    }

    public void setInitialPoint() {
        this.points = INITIAL_POINT;
    }

    public void ask(Question question) {
        this.points = this.points.deduct(question.getPoints());
    }

    public Long getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }

    public DisplayName getDisplayName() {
        return displayName;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public About getAbout() {
        return about;
    }

    public Points getPoints() {
        return points;
    }

    public ImageUrl getImageUrl() {
        return imageUrl;
    }

    public Name getName() {
        return name;
    }

    public List<FollowerId> getFollowerIds() {
        return followerIds;
    }

    public List<ScrapId> getScrapIds() {
        return scrapIds;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean authenticate(Password password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password.value(), this.password.value());
    }

    public UserDto toDto() {
        return new UserDto(this.id,
                this.displayName.value(),
                this.about.value(),
                this.points.value(),
                this.name.value(),
                this.imageUrl.value());
    }

    public UserCreationDto toCreationDto() {
        return new UserCreationDto(id, displayName.value());
    }

    public static User fake() {
        User user = new User(new DisplayName("joo"), new Email("test@example.com"));

        user.setInitialPoint();

        return user;
    }
}
