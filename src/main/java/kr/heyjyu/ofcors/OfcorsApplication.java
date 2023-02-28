package kr.heyjyu.ofcors;

import kr.heyjyu.ofcors.interceptors.AuthenticationInterceptor;
import kr.heyjyu.ofcors.utils.IamPort;
import kr.heyjyu.ofcors.utils.JwtUtil;
import kr.heyjyu.ofcors.utils.KakaoPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class OfcorsApplication {
	@Value("${jwt.secret}")
	private String secret;

	@Value("${kakaopay.admin-key}")
	private String kakaopayAdminKey;

	@Value("${iamport.api-key}")
	private String iamportApiKey;

	@Value("${iamport.api-secret}")
	private String iamportApiSecret;

	@Value("${kakaopay.redirect-base-url}")
	private String kakaoPayRedirectBaseUrl;

	public static void main(String[] args) {
		SpringApplication.run(OfcorsApplication.class, args);
	}

	@Bean
	public WebSecurityCustomizer ignoringCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/**");
	}

	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {
			@Autowired
			private HandlerInterceptor backdoorControlInterceptor;

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
			}

			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(authenticationInterceptor());
				registry.addInterceptor(backdoorControlInterceptor);
			}
		};
	}

	@Bean
	public HandlerInterceptor authenticationInterceptor() {
		return new AuthenticationInterceptor(jwtUtil());
	}

	@Bean
	public JwtUtil jwtUtil() {
		return new JwtUtil(secret);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new Argon2PasswordEncoder(16, 32, 1, 1 << 14, 2);
	}

	@Bean
	public KakaoPay kakaoPay() {
		return new KakaoPay(kakaopayAdminKey, kakaoPayRedirectBaseUrl);
	}

	@Bean
	public IamPort iamPort() {
		return new IamPort(iamportApiKey, iamportApiSecret);
	}
}
