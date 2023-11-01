package com.app.usermicroservice.userRepository;

import com.app.usermicroservice.userDomain.Account;
import com.app.usermicroservice.userDomain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query("UPDATE User u  SET u.name=:name WHERE u.id=:id")
    int updateNameById(@Param("id")Long id, @Param("name")String name);

}
