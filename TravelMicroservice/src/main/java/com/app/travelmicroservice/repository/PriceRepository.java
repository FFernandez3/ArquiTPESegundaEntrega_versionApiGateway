package com.app.travelmicroservice.repository;

import com.app.travelmicroservice.domain.Price;
import com.app.travelmicroservice.domain.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    @Query("UPDATE Price p SET p.regularFee=:regularFee WHERE p.id=:id")
    Travel updateRegularFeeById(Long id, Double regularFee);
}
