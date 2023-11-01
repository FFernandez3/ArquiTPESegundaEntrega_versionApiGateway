package com.app.usermicroservice.userService;

import com.app.usermicroservice.dto.AccountDTO;
import com.app.usermicroservice.dto.UserDTO;
import com.app.usermicroservice.userDomain.Account;
import com.app.usermicroservice.userDomain.User;
import com.app.usermicroservice.userRepository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service

public class AccountService {
    private final AccountRepository accountRepository;
    private final RestTemplate restTemplate;

    public AccountService(AccountRepository ar, RestTemplate restTemplate){
        this.accountRepository=ar;
        this.restTemplate=restTemplate;
    }
    @Transactional
    public Account save(Account entity) throws Exception{
        try {
            return this.accountRepository.save(entity);
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    public List<AccountDTO> findAll() {
        return this.accountRepository.findAll().stream()
                .map(account -> new AccountDTO(account.getId(), account.getAmount(),
                        account.getDate()))
                .toList();

    }
    public Optional<AccountDTO> findById(Long id) {
        return this.accountRepository.findById(id).map(account -> new AccountDTO(account.getId(), account.getAmount(),
                account.getDate()));

    }
@Transactional
    public Optional<Account> deleteById(Long id) {
        Optional<Account> entityToDelete = accountRepository.findById(id);
        entityToDelete.ifPresent(entity -> accountRepository.deleteById(id));
        return entityToDelete;
    }
    @Transactional
    public int updateDateById(Long id, LocalDate date){
        //Optional<Account> entityToUpdate = accountRepository.findById(id);
        //entityToUpdate.ifPresent(entity -> accountRepository.updateDateById(id, date));
        return this.accountRepository.updateDateById(id, date);
    }

    @Transactional
    public int updateIsCanceledById(Long id, boolean isCanceled){
        //Optional<Account> entityToUpdate = accountRepository.findById(id);
        //entityToUpdate.ifPresent(entity -> accountRepository.updateIsCanceledById(id, isCanceled));
        return this.accountRepository.updateIsCanceledById(id, isCanceled);
    }
    public List<Long> getIdAccounts(){
        return this.accountRepository.getIdAccounts();
    }
}
