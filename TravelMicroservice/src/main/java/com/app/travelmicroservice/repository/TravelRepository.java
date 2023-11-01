package com.app.travelmicroservice.repository;

import com.app.travelmicroservice.domain.Travel;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long> {
    @Modifying
    @Query("UPDATE Travel t  SET t.idScooter=:idScooter WHERE t.id=:id")
    int updateScooterById(@Param("id") Long id,@Param("idScooter") Long idScooter);


    @Query("Select t FROM Travel t WHERE t.idScooter=:id")
    List<Travel> getTravelsByScooterId(Long id);

    @Modifying
    @Query("UPDATE Travel t  SET t.finishDateTime=:finishDateTime WHERE t.id=:id")
    int updateFinishDateTimeById(@Param("id")Long id, @Param("finishDateTime") LocalDateTime finishDateTime);

    //buscar viajes en un rango de meses de un año
    @Query("FROM Travel t WHERE (MONTH (t.finishDateTime) BETWEEN :month1 AND :month2) AND YEAR(t.finishDateTime)=:year")
    List<Travel>getTravelsByMonthAndYear(Integer month1, Integer month2, Integer year);

    @Query("SELECT YEAR(t.finishDateTime) AS yearResult, t.idScooter AS id, COUNT(*) AS trip FROM Travel t \n" +
            "WHERE YEAR(t.finishDateTime) = :year \n" +
            "GROUP BY YEAR(t.finishDateTime), t.idScooter\n" +
            "HAVING COUNT(*) > :quantity")
    List<Tuple>getTravelsByYear(Long year, Long quantity);

}
