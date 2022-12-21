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

    @GetMapping("setup-database")
    public String setupDatabase() {
        LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.execute("DELETE FROM users");

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
}
