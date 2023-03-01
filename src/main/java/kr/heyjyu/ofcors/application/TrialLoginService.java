package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.models.DisplayName;
import kr.heyjyu.ofcors.models.Email;
import kr.heyjyu.ofcors.models.ImageUrl;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class TrialLoginService {
    private UserRepository userRepository;

    public TrialLoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login() {
        Email email = new Email("tester@example.com");

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }

        DisplayName displayName = new DisplayName("체험자");
        ImageUrl imageUrl = new ImageUrl("https://ui-avatars.com/api/?name=" + displayName.value() + "&background=0D8ABC&color=fff");

        User user = new User(displayName, email);
        user.changeImageUrl(imageUrl);
        user.setInitialPoint();

        userRepository.save(user);

        return user;
    }
}
