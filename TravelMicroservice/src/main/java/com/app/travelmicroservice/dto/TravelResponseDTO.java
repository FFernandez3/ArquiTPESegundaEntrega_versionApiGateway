package com.app.travelmicroservice.dto;

import com.app.travelmicroservice.domain.Price;
import com.app.travelmicroservice.domain.Travel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class TravelResponseDTO {

    private Long id;

    private Long idUser;

    private Long idScooter;

    private LocalDateTime startDateTime;

    private LocalDateTime finishDateTime;

    private Integer pause;
    private Double kilometers;

    private Price price;



    public TravelResponseDTO(Long id, Long idUser, Long idScooter, LocalDateTime startDateTime, LocalDateTime finishDateTime, Integer pause, Price price, Double km) {
        this.id =id;
        this.idUser = idUser;
        this.idScooter = idScooter;
        this.startDateTime = startDateTime;
        this.finishDateTime = finishDateTime;
        this.pause = pause;
        this.price = price;
        this.kilometers=km;
    }

    public TravelResponseDTO(Long id, LocalDateTime startDateTime, LocalDateTime finishDateTime, Double kilometers, Integer pause ) {
        this.id = id;
        this.startDateTime = startDateTime;
        this.finishDateTime = finishDateTime;
        this.kilometers=kilometers;
        this.pause=pause;
    }

    public TravelResponseDTO(Long id, LocalDateTime finishDateTime, Integer pause, Double kilometers, Price price) {
        this.id = id;
        this.finishDateTime = finishDateTime;
        this.pause = pause;
        this.kilometers = kilometers;
        this.price = price;
    }
    public TravelResponseDTO(Travel travel){
        this.id=travel.getId();
        this.startDateTime = travel.getStartDateTime();
        this.finishDateTime = travel.getFinishDateTime();
        this.price= travel.getPrice();
        this.idUser = travel.getIdUser();
        this.idScooter = travel.getIdScooter();
        this.pause = travel.getPause();
        this.kilometers = travel.getKilometers();

    }


}
