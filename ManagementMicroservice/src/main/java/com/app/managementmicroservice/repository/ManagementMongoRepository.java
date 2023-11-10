package com.app.managementmicroservice.repository;

import com.app.managementmicroservice.domain.Manager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ManagementMongoRepository extends MongoRepository<Manager, String> {


   /* @Query("{'_id' : ?0}, {$set: { 'role' : ?1 }}")
    void updateRoleManagerById(@Param("id") Long id, @Param("role") String role);*/
}
