package com.app.travelmicroservice.domain;

import com.app.travelmicroservice.dto.PriceRequestDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDate date;
    @Column
    private Double regularFee;
    @Column
    private Double extraFee;

    public Price() {
    }
    public Price(PriceRequestDTO requestDTO){
        this.date=requestDTO.getDate();
        this.regularFee=requestDTO.getRegularFee();
        this.extraFee=requestDTO.getExtraFee();
    }

    public Price( LocalDate date, Double regularFee, Double extraFee) {
        this.date = date;
        this.regularFee = regularFee;
        this.extraFee = extraFee;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getRegularFee() {
        return regularFee;
    }

    public void setRegularFee(Double regularFee) {
        this.regularFee = regularFee;
    }

    public Double getExtraFee() {
        return extraFee;
    }

    public void setExtraFee(Double extraFee) {
        this.extraFee = extraFee;
    }



    public Long getId() {
        return id;
    }
}
