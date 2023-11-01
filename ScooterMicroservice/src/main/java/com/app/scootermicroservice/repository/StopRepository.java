package com.app.scootermicroservice.repository;


import com.app.scootermicroservice.domain.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StopRepository extends JpaRepository<Stop, Long> {
    @Query("UPDATE Stop s  SET s.longitude=:longitude WHERE s.id=:id")
    Stop updateLongitudeById(Long id, Double longitude);

    @Query("SELECT s FROM Stop s where (s.latitude BETWEEN :latitudeMin and :latitudeMax) AND (s.longitude between :longitudeMin and :longitudeMax)")
    List<Stop> getStopsNear(@Param("latitudeMin") Double latitudeMin, @Param("latitudeMax")Double latitudeMax, @Param("longitudeMin")Double longitudeMin, @Param("longitudeMax") Double longitudeMax);
}
