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
        jdbcTemplate.execute("DELETE FROM question_tags");
        jdbcTemplate.execute("DELETE FROM question");

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
                "저는 함수형 프로그래밍을 좋아합니다", "joo", "tester@example.com", "", "홍길동", passwordEncoder.encode("Abcdef1!"), 100L,
                now, now
        );

        jdbcTemplate.update("" +
                        "INSERT INTO users(" +
                        "  id, about, display_name, email, image_url, name," +
                        "  encoded_password, points, created_at, updated_at" +
                        ")" +
                        " VALUES(2, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                "저는 함수형 프로그래밍을 좋아합니다", "joo2", "tester2@example.com", "", "홍동길", passwordEncoder.encode("Abcdef1!"), 100L,
                now, now
        );

        return "OK";
    }

    @GetMapping("write-questions")
    public String writeQuestions() {
        LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.execute("DELETE FROM question_like_user_ids");
        jdbcTemplate.execute("DELETE FROM question_tags");
        jdbcTemplate.execute("DELETE FROM question");

        jdbcTemplate.update("" +
                        "INSERT INTO question(" +
                        "  id, author_id, body, hits, points, status," +
                        "  title, created_at, updated_at" +
                        ")" +
                        " VALUES(1, ?, ?, ?, ?, ?, ?, ?, ?)",
                1L, "서버 배포 후 CORS에러가 발생합니다.", 3L, 50L, "OPEN", "No 'Access-Control-Allow-Origin' 에러가 뜹니다",
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
                1L, "서버 배포 후 CORS에러가 발생합니다.", 3L, 30L, "OPEN", "No 'Access-Control-Allow-Origin' 에러가 뜹니다",
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
                1L, "서버 배포 후 CORS에러가 발생합니다.", 3L, 30L, "CLOSED", "No 'Access-Control-Allow-Origin' 에러가 뜹니다",
                now.minusWeeks(2), now.minusWeeks(2)
        );

        jdbcTemplate.update("" +
                        "INSERT INTO question(" +
                        "  id, author_id, body, hits, points, status," +
                        "  title, created_at, updated_at" +
                        ")" +
                        " VALUES(4, ?, ?, ?, ?, ?, ?, ?, ?)",
                1L, "서버 배포 후 CORS에러가 발생합니다.", 1L, 30L, "CLOSED", "No 'Access-Control-Allow-Origin' 에러가 뜹니다",
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
                1L, "서버 배포 후 CORS에러가 발생합니다.", 1L, 30L, "CLOSED", "No 'Access-Control-Allow-Origin' 에러가 뜹니다",
                now, now
        );

        return "OK";
    }
}
