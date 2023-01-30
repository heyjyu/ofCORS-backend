package kr.heyjyu.ofcors.models;

import kr.heyjyu.ofcors.exceptions.NotEnoughPoints;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.map;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {
    @Test
    void changePassword() {
        PasswordEncoder passwordEncoder = new Argon2PasswordEncoder(16, 32, 1, 1 << 14, 2);

        User user = User.fake();

        user.changePassword(new Password("Abcdef1!"), passwordEncoder);

        assertThat(user.getPassword()).isNotNull();
        assertThat(user.getPassword()).isNotEqualTo(new Password("Abcdef1!"));
    }

    @Test
    void changeImageUrl() {
        User user = User.fake();

        ImageUrl imageUrl = new ImageUrl("https://ui-avatars.com/api/?name=Joo&background=0D8ABC&color=fff");

        user.changeImageUrl(imageUrl);

        assertThat(user.getImageUrl()).isEqualTo(imageUrl);
    }

    @Test
    void editProfile() {
        User user = User.fake();

        DisplayName displayName = new DisplayName("hong");
        About about = new About("저는 함수형 프로그래밍을 좋아합니다");
        ImageUrl imageUrl = new ImageUrl("https://image.com");
        Set<Tag> tags = Set.of(new Tag("Web"));

        user.editProfile(displayName, about, imageUrl, tags);

        assertThat(user.getDisplayName()).isEqualTo(displayName);
    }

    @Test
    void setInitialPoint() {
        User user = new User();

        user.setInitialPoint();

        assertThat(user.getPoints()).isEqualTo(User.INITIAL_POINT);
    }

    @Test
    void authenticate() {
        PasswordEncoder passwordEncoder = new Argon2PasswordEncoder(16, 32, 1, 1 << 14, 2);

        User user = new User();

        user.changePassword(new Password("Abcdef1!"), passwordEncoder);

        assertThat(user.authenticate(new Password("Abcdef1!"), passwordEncoder)).isTrue();
        assertThat(user.authenticate(new Password("Abcdef1!!"), passwordEncoder)).isFalse();
    }

    @Test
    void ask() {
        User user = new User();
        user.setInitialPoint();

        Question question = Question.fake();

        user.ask(question);

        assertThat(user.getPoints())
                .isEqualTo(new Points(User.INITIAL_POINT.value() - question.getPoints().value()));
    }

    @Test
    void askWithNotEnoughPoints() {
        User user = new User();
        user.setInitialPoint();

        AuthorId authorId = new AuthorId(1L);
        Title title = new Title("CORS에러가 발생합니다.");
        Body body = new Body("서버 배포 후 CORS에러가 발생합니다.");
        Set<Tag> tags = Set.of(new Tag("Web"));
        Points points = new Points(10000L);

        Question question = new Question(authorId, title, body, tags, points);

        assertThrows(NotEnoughPoints.class, () -> user.ask(question));
    }

    @Test
    void transfer() {
        User sender = User.fake();
        User receiver = User.fake();
        Points points = new Points(10L);

        sender.transfer(receiver, points);

        assertThat(sender.getPoints()).isEqualTo(User.INITIAL_POINT.deduct(points));
        assertThat(receiver.getPoints()).isEqualTo(User.INITIAL_POINT.add(points));
    }

    @Test
    void receive() {
        User user = User.fake();
        Points points = new Points(10L);

        user.receive(points);
        assertThat(user.getPoints()).isEqualTo(User.INITIAL_POINT.add(points));
    }

    @Test
    void transferExcessiveAmount() {
        User sender = User.fake();
        User receiver = User.fake();
        Points points = new Points(1000L);

        assertThrows(NotEnoughPoints.class, () -> sender.transfer(receiver, points));
    }

    @Test
    void buyPoints() {
        User user = User.fake();

        Points initialPoints = user.getPoints();

        Points points = new Points(10L);

        user.buyPoints(points);

        assertThat(user.getPoints()).isEqualTo(initialPoints.add(points));
    }
}
