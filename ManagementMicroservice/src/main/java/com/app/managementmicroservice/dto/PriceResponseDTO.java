package com.app.managementmicroservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceResponseDTO {
    private Long id;
    private LocalDate date;
    private Double regularFee;
    private Double extraFee;


    public PriceResponseDTO(LocalDate date, Double regularFee, Double extraFee) {
        this.date = date;
        this.regularFee = regularFee;
        this.extraFee = extraFee;
    }

}
