package kr.heyjyu.ofcors.backdoor;

import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@RestController
@RequestMapping("backdoor")
@Transactional
public class BackdoorController {
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    public BackdoorController(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("truncate")
    public String truncate() {
        jdbcTemplate.execute("DELETE FROM user_tags");
        jdbcTemplate.execute("DELETE FROM user_scrap_ids");
        jdbcTemplate.execute("DELETE FROM user_follower_ids");
        jdbcTemplate.execute("DELETE FROM users");

        jdbcTemplate.execute("DELETE FROM question_like_user_ids");
        jdbcTemplate.execute("DELETE FROM question_scrap_user_ids");
        jdbcTemplate.execute("DELETE FROM question_tags");
        jdbcTemplate.execute("DELETE FROM question");

        jdbcTemplate.execute("DELETE FROM answer_like_user_ids");
        jdbcTemplate.execute("DELETE FROM answer");

        jdbcTemplate.execute("DELETE FROM exchange");

        return "OK";
    }

    @GetMapping("setup-database")
    public String setupDatabase() {
        LocalDateTime now = LocalDateTime.now();

        truncate();

        jdbcTemplate.update("" +
                        "INSERT INTO users(" +
                        "  id, about, display_name, email, image_url, name," +
                        "  encoded_password, points, created_at, updated_at" +
                        ")" +
                        " VALUES(1, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                "?????? ????????? ?????????????????? ???????????????", "joo", "tester@example.com", "https://ui-avatars.com/api/?name=joo&background=0D8ABC&color=fff", "", passwordEncoder.encode("Abcdef1!"), 900L,
                now, now
        );

        jdbcTemplate.update("" +
                        "INSERT INTO user_tags(" +
                        "  user_id, tag" +
                        ")" +
                        " VALUES(?, ?)",
                1L, "Web"
        );

        jdbcTemplate.update("" +
                        "INSERT INTO users(" +
                        "  id, about, display_name, email, image_url, name," +
                        "  encoded_password, points, created_at, updated_at" +
                        ")" +
                        " VALUES(2, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                "?????? ????????? ?????????????????? ???????????????", "joo2", "tester2@example.com", "https://ui-avatars.com/api/?name=joo2&background=0D8ABC&color=fff", "?????????", passwordEncoder.encode("Abcdef1!"), 100L,
                now, now
        );

        jdbcTemplate.update("" +
                        "INSERT INTO users(" +
                        "  id, about, display_name, email, image_url, name," +
                        "  encoded_password, points, created_at, updated_at" +
                        ")" +
                        " VALUES(3, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                "?????? ????????? ?????????????????? ???????????????", "joo2", "tester3@example.com", "https://ui-avatars.com/api/?name=joo3&background=0D8ABC&color=fff", "?????????", passwordEncoder.encode("Abcdef1!"), 100L,
                now, now
        );

        return "OK";
    }

    @GetMapping("write-questions")
    public String writeQuestions() {
        LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.execute("DELETE FROM question_like_user_ids");
        jdbcTemplate.execute("DELETE FROM question_scrap_user_ids");
        jdbcTemplate.execute("DELETE FROM question_tags");
        jdbcTemplate.execute("DELETE FROM question");

        jdbcTemplate.update("" +
                        "INSERT INTO question(" +
                        "  id, author_id, body, hits, points, status," +
                        "  title, created_at, updated_at" +
                        ")" +
                        " VALUES(1, ?, ?, ?, ?, ?, ?, ?, ?)",
                1L, "?????? ?????? ??? CORS????????? ???????????????.", 3L, 50L, "OPEN", "No 'Access-Control-Allow-Origin' ????????? ?????????",
                now.minusWeeks(2), now.minusWeeks(2)
        );

        jdbcTemplate.update("" +
                        "INSERT INTO question_like_user_ids(" +
                        "  question_id, like_user_id" +
                        ")" +
                        " VALUES(?, ?)",
                1L, 2L
        );

        jdbcTemplate.update("" +
                        "INSERT INTO question_like_user_ids(" +
                        "  question_id, like_user_id" +
                        ")" +
                        " VALUES(?, ?)",
                1L, 3L
        );

        jdbcTemplate.update("" +
                        "INSERT INTO question(" +
                        "  id, author_id, body, hits, points, status," +
                        "  title, created_at, updated_at" +
                        ")" +
                        " VALUES(2, ?, ?, ?, ?, ?, ?, ?, ?)",
                1L, "?????? ?????? ??? CORS????????? ???????????????.", 3L, 30L, "OPEN", "No 'Access-Control-Allow-Origin' ????????? ?????????",
                now, now
        );

        jdbcTemplate.update("" +
                        "INSERT INTO question_like_user_ids(" +
                        "  question_id, like_user_id" +
                        ")" +
                        " VALUES(?, ?)",
                2L, 1L
        );

        jdbcTemplate.update("" +
                        "INSERT INTO question_tags(" +
                        "  question_id, tag" +
                        ")" +
                        " VALUES(?, ?)",
                2L, "Web"
        );

        jdbcTemplate.update("" +
                        "INSERT INTO question(" +
                        "  id, author_id, body, hits, points, status," +
                        "  title, created_at, updated_at" +
                        ")" +
                        " VALUES(3, ?, ?, ?, ?, ?, ?, ?, ?)",
                1L, "?????? ?????? ??? CORS????????? ???????????????.", 3L, 30L, "CLOSED", "No 'Access-Control-Allow-Origin' ????????? ?????????",
                now.minusWeeks(2), now.minusWeeks(2)
        );

        jdbcTemplate.update("" +
                        "INSERT INTO question(" +
                        "  id, author_id, body, hits, points, status," +
                        "  title, created_at, updated_at" +
                        ")" +
                        " VALUES(4, ?, ?, ?, ?, ?, ?, ?, ?)",
                1L, "?????? ?????? ??? CORS????????? ???????????????.", 1L, 30L, "CLOSED", "No 'Access-Control-Allow-Origin' ????????? ?????????",
                now.minusWeeks(1), now.minusWeeks(1)
        );

        jdbcTemplate.update("" +
                        "INSERT INTO question_like_user_ids(" +
                        "  question_id, like_user_id" +
                        ")" +
                        " VALUES(?, ?)",
                4L, 1L
        );

        jdbcTemplate.update("" +
                        "INSERT INTO question(" +
                        "  id, author_id, body, hits, points, status," +
                        "  title, created_at, updated_at" +
                        ")" +
                        " VALUES(5, ?, ?, ?, ?, ?, ?, ?, ?)",
                1L, "?????? ?????? ??? CORS????????? ???????????????.", 1L, 30L, "CLOSED", "No 'Access-Control-Allow-Origin' ????????? ?????????",
                now, now
        );

        return "OK";
    }
}
