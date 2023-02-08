package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.exceptions.UserNotFound;
import kr.heyjyu.ofcors.models.Name;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.models.UserId;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ChangeNameService {
    private UserRepository userRepository;

    public ChangeNameService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void changeName(UserId userId, Name name) {
        User user = userRepository.findById(userId.value())
                .orElseThrow(() -> new UserNotFound(userId));

        user.changeName(name);
    }
}
