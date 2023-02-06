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
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private Set<Tag> tags = new HashSet<>();

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

    @Formula("(SELECT COUNT(*) " +
            "FROM question_like_user_ids ql " +
            "JOIN question q " +
            "ON ql.question_id = q.id " +
            "WHERE q.author_id = id" +
            ") " +
            "+ " +
            "(SELECT COUNT(*) " +
            "FROM answer_like_user_ids al " +
            "JOIN answer a " +
            "ON al.answer_id = a.id " +
            "WHERE a.author_id = id" +
            ")")
    private Long countOfLikes;

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
        this.name = new Name("홍길동");
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

    public Set<Tag> getTags() {
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

    public Long getCountOfLikes() {
        return countOfLikes;
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

    public void changeImageUrl(ImageUrl url) {
        this.imageUrl = url;
    }

    public void transfer(User receiver, Points points) {
        if (!this.points.isAffordable(points)) {
            throw new NotEnoughPoints();
        }

        this.points = this.points.deduct(points);
        receiver.points = receiver.points.add(points);
    }

    public UserDto toDto() {
        return new UserDto(this.id,
                this.displayName.value(),
                this.about.value(),
                this.points.value(),
                this.name.value(),
                this.imageUrl.value(),
                this.countOfLikes,
                this.tags.stream().map(Tag::toDto).collect(Collectors.toSet()));
    }

    public UserCreationDto toCreationDto() {
        return new UserCreationDto(id, displayName.value());
    }

    public static User fake() {
        User user = new User(new DisplayName("joo"), new Email("test@example.com"));

        user.setInitialPoint();

        return user;
    }

    public static User fake(Points points) {
        User user = new User(new DisplayName("joo"), new Email("test@example.com"));

        user.points = points;

        return user;
    }

    public void receive(Points points) {
        this.points = this.points.add(points);
    }

    public void editProfile(DisplayName displayName, About about, ImageUrl imageUrl, Set<Tag> tags) {
        this.displayName = displayName;
        this.about = about;
        this.imageUrl = imageUrl;
        this.tags = tags;
    }

    public void buyPoints(Points points) {
        this.points = this.points.add(points);
    }

    public void exchangePoints(Quantity quantity) {
        if (this.points.value() < quantity.value()) {
            throw new NotEnoughPoints();
        }

        this.points = this.points.deduct(new Points(quantity.value()));
    }
}
