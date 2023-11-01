package com.app.travelmicroservice.dto;

import com.app.travelmicroservice.domain.Price;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TravelRequestDTO {

    private Long idUser;

    private Long idScooter;

    private LocalDateTime startDateTime;

    private LocalDateTime finishDateTime;

    private Integer pause;

    private Double kilometers;

    private Long priceId;


}