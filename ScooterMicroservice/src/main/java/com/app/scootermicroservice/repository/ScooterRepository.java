package com.app.scootermicroservice.repository;

import com.app.scootermicroservice.domain.Scooter;
import com.app.scootermicroservice.domain.Stop;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScooterRepository extends JpaRepository<Scooter, Long> {
    @Query("SELECT s FROM Scooter s WHERE s.stop=:idStop")
    List<Scooter>getScooterByStop(Stop idStop);

    @Query("SELECT s.id FROM Scooter s")
    List<Long>getScooterIds();

    @Query("SELECT s FROM Scooter s where (s.latitude BETWEEN :latitudeMin and :latitudeMax) AND (s.longitude between :longitudeMin and :longitudeMax)")
    List<Scooter> getScootersNear(@Param("latitudeMin") Double latitudeMin, @Param("latitudeMax")Double latitudeMax, @Param("longitudeMin")Double longitudeMin, @Param("longitudeMax") Double longitudeMax);
    @Modifying
    @Query("UPDATE Scooter s  SET s.isAvailable=:isAvailable WHERE s.id=:id")
    int updateIsAvailableById(@Param("id") Long id, @Param("isAvailable") boolean isAvailable);
    @Modifying
    @Query("UPDATE Scooter s SET s.kilometersTraveled=:kilometersTraveled WHERE s.id=:id")
    int updateKilometersById(@Param("id")Long id, @Param("kilometersTraveled")Double kilometersTraveled);
    @Modifying
    @Query("UPDATE Scooter s SET s.timeWithBreaks=:timeWithBreaks WHERE s.id=:id")
    int updateTimeWithBreaksById(@Param("id") Long id,@Param("timeWithBreaks") Double timeWithBreaks);
    @Modifying
    @Query("UPDATE Scooter s SET s.timeWithoutBreaks=:timeWithoutBreaks WHERE s.id=:id")
    int updateTimeWithoutBreaksById(@Param("id") Long id,@Param("timeWithoutBreaks") Double timeWithoutBreaks);

    @Query("SELECT s.id, s.kilometersTraveled FROM Scooter s ORDER BY s.kilometersTraveled DESC")
    List<Scooter> getAllOrderByKm();

    @Query("SELECT s.id, s.timeWithBreaks, s.isAvailable FROM Scooter s ORDER BY s.timeWithBreaks DESC")
    List<Scooter> getAllOrderByTimeWithBreaks();

    @Query("SELECT s.id, s.timeWithoutBreaks, s.lastMaintenance FROM Scooter s ORDER BY s.timeWithoutBreaks DESC")
    List<Scooter> getAllOrderByTimeWithoutBreaks();

    //@Query("SELECT s.isAvailable, COUNT(s) FROM Scooter s GROUP BY s.isAvailable")
    @Query("SELECT SUM(CASE WHEN s.isAvailable = false THEN 1 ELSE 0 END)AS unavailable_count, SUM(CASE WHEN s.isAvailable = true THEN 1 ELSE 0 END) AS available_count  FROM Scooter s")
    Tuple getQuantityScootersByAvailability();


}
