package com.app.usermicroservice.utils;

import com.app.usermicroservice.userDomain.Account;
import com.app.usermicroservice.userDomain.User;
import com.app.usermicroservice.userDomain.UserAccount;
import com.app.usermicroservice.userRepository.AccountRepository;
import com.app.usermicroservice.userRepository.UserAccountRepository;
import com.app.usermicroservice.userRepository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;


@Configuration
public class LoadDataBase {
    private static final Logger log = LoggerFactory.getLogger(LoadDataBase.class);
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, AccountRepository accountRepository, UserAccountRepository userAccountRepository) {
        return args -> {
          /*  User u1=new User("Florencia", "Fernandez", 2782919L,800.989, 984.387, "flor@gmail.com");
            User u2=new User("Maria Ines", "Conti", 928271L, 847.093, 982.839, "mari@gmail.com");
            User u3=new User("Carlos", "Rodriguez", 223877L,837.873, 983.093, "charly@gmail.com");
            User u4=new User("Horacio", "Larreta", 5389711L,809.983, 902.763, "larry@gmail.com");

            Account a1=new Account(553, LocalDate.of(2020,2,15));
            Account a2=new Account(7045, LocalDate.of(2021,6,8));
            Account a3=new Account(4653, LocalDate.of(2021,8,25));

            UserAccount ua1=new UserAccount(u1, a2);
            UserAccount ua2=new UserAccount(u2, a1);
            UserAccount ua3=new UserAccount(u3, a3);

            log.info("Preloading " + userRepository.save(u1));
            log.info("Preloading " + userRepository.save(u2));
            log.info("Preloading " + userRepository.save(u3));
            log.info("Preloading " + userRepository.save(u4));
            log.info("Preloading " + accountRepository.save(a1));
            log.info("Preloading " + accountRepository.save(a2));
            log.info("Preloading " + accountRepository.save(a3));
            log.info("Preloading " + userAccountRepository.save(ua1));
            log.info("Preloading " + userAccountRepository.save(ua2));
            log.info("Preloading " + userAccountRepository.save(ua3));*/



        };

}}
