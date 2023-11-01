package com.app.travelmicroservice.dto;



import com.app.travelmicroservice.domain.Price;
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

    public PriceResponseDTO(Price p){
        this.id=p.getId();
        this.date=p.getDate();
        this.extraFee=p.getExtraFee();
        this.regularFee=p.getRegularFee();
    }
    public PriceResponseDTO(LocalDate date, Double regularFee, Double extraFee) {
        this.date = date;
        this.regularFee = regularFee;
        this.extraFee = extraFee;
    }



}
