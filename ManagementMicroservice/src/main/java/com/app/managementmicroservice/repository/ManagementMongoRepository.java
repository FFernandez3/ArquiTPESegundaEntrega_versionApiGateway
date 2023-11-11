package com.app.managementmicroservice.repository;

import com.app.managementmicroservice.domain.Manager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ManagementMongoRepository extends MongoRepository<Manager, String> {


   /* @Query("{'_id' : ?0}, {$set: { 'role' : ?1 }}")
    void updateRoleManagerById(@Param("id") Long id, @Param("role") String role);*/

    @Query("{ 'email' : { '$regex' : ?0, '$options' : 'i' } }")
    Optional<Manager> findManagerByEmailIgnoreCase(String email);

    @Query(value = "{ 'email' : { '$regex' : ?0, '$options' : 'i' } }", count = true)
    boolean existsManagersByEmailIgnoreCase(String email );
}
