package com.app.usermicroservice.userRepository;

import com.app.usermicroservice.userDomain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Modifying
    @Query("UPDATE Account a  SET a.date=:date WHERE a.id=:id")
    int updateDateById(@Param("id")Long id, @Param("date") LocalDate date);

    @Modifying
    @Query("UPDATE Account a  SET a.isCanceled=:isCanceled WHERE a.id=:id")
    int updateIsCanceledById(@Param("id") Long id,@Param("isCanceled") boolean isCanceled);

    @Query("SELECT a.id FROM Account a")
    List<Long> getIdAccounts();

}
