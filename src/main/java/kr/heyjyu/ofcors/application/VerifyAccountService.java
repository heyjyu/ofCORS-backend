package kr.heyjyu.ofcors.application;

import jakarta.transaction.Transactional;
import kr.heyjyu.ofcors.dtos.AccountVerificationResultDto;
import kr.heyjyu.ofcors.exceptions.UserNotFound;
import kr.heyjyu.ofcors.models.AccountNumber;
import kr.heyjyu.ofcors.models.Bank;
import kr.heyjyu.ofcors.models.User;
import kr.heyjyu.ofcors.repositories.UserRepository;
import kr.heyjyu.ofcors.utils.IamPort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class VerifyAccountService {
    private UserRepository userRepository;
    private IamPort iamPort;

    public VerifyAccountService(UserRepository userRepository, IamPort iamPort) {
        this.userRepository = userRepository;
        this.iamPort = iamPort;
    }

    public AccountVerificationResultDto verify(Long userId, Bank bank, AccountNumber accountNumber) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound(userId));

        try {
            String bankHolder = iamPort.getBankHolder(bank.value(), accountNumber.value());

            // TODO: 실명과 예금주 비교하기
            // user.isBankHolder(bankHolder);
            return new AccountVerificationResultDto(true);
        } catch (RuntimeException e) {
            return new AccountVerificationResultDto(false);
        }
    }
}
