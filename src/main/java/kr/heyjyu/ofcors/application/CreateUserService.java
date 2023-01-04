package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.exceptions.ExistingEmail;
import kr.heyjyu.ofcors.models.DisplayName;
import kr.heyjyu.ofcors.models.Email;
import kr.heyjyu.ofcors.models.ImageUrl;
import kr.heyjyu.ofcors.models.Password;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CreateUserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public CreateUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(DisplayName displayName, Email email, Password password) {
        if (userRepository.existsByEmail(email)) {
            throw new ExistingEmail(email);
        }

        ImageUrl imageUrl = new ImageUrl("https://ui-avatars.com/api/?name=" + displayName + "&background=0D8ABC&color=fff");

        User user = new User(displayName, email);
        user.changePassword(password, passwordEncoder);
        user.changeImageUrl(imageUrl);
        user.setInitialPoint();

        userRepository.save(user);

        return user;
    }
}
