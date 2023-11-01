package com.app.usermicroservice.userService;

import com.app.usermicroservice.userDomain.Account;
import com.app.usermicroservice.userDomain.User;
import com.app.usermicroservice.userDomain.UserAccount;
import com.app.usermicroservice.userRepository.UserAccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class UserAccountService {
    private final UserAccountRepository userAccountRepository;


    public UserAccountService(UserAccountRepository uar){
        this.userAccountRepository=uar;
    }

    @Transactional
    public UserAccount save(UserAccount entity) throws Exception{
        try {
            return this.userAccountRepository.save(entity);
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    @Transactional
    public Optional<UserAccount> deleteById(Long id) {
        Optional<UserAccount> entityToDelete = userAccountRepository.findById(id);
        entityToDelete.ifPresent(entity -> userAccountRepository.deleteById(id));
        return entityToDelete;
    }

}
