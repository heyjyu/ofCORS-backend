package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.models.Email;
import kr.heyjyu.ofcors.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CountUserService {
    private UserRepository userRepository;

    public CountUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Integer count(Email email) {
        return userRepository.findAllByEmail(email).size();
    }
}
