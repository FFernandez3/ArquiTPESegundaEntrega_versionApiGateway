package com.app.scootermicroservice.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.ANY)
public class TravelResponseDTO {
    private Long id;
    private Long idUser;
    private Long idScooter;
    private LocalDateTime startDateTime;

    private LocalDateTime finishDateTime;

    private Integer pause;
    private Double kilometers;

    public TravelResponseDTO(Long id, LocalDateTime startDateTime, LocalDateTime finishDateTime, Double kilometers, Integer pause) {
        this.id = id;
        this.startDateTime = startDateTime;
        this.finishDateTime = finishDateTime;
        this.pause = pause;
        this.kilometers = kilometers;
    }

}
