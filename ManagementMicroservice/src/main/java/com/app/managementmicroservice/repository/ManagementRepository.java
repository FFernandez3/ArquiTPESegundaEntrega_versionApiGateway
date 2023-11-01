package com.app.managementmicroservice.repository;

import com.app.managementmicroservice.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagementRepository extends JpaRepository<Manager, Long> {

    @Query("UPDATE Manager m set m.role = :role WHERE m.id = :id")
    int updateRoleManagerById(@Param("id")Long id, @Param("role")String role);
}
