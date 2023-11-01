package com.app.travelmicroservice.domain;

import com.app.travelmicroservice.dto.TravelRequestDTO;
import com.app.travelmicroservice.dto.TravelResponseDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Timer;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long idUser;
    @Column
    private Long idScooter;
    @Column
    private LocalDateTime startDateTime;
    @Column
    private LocalDateTime finishDateTime;
    @Column
    private Integer pause;
    @Column
    private Double kilometers;

    @ManyToOne
    @JoinColumn(unique=false)
    private Price price;



    public Travel(TravelRequestDTO travelRequestDTO){
        this.idUser = travelRequestDTO.getIdUser();
        this.idScooter = travelRequestDTO.getIdScooter();
        this.startDateTime = travelRequestDTO.getStartDateTime();
        this.finishDateTime = travelRequestDTO.getFinishDateTime();
        this.pause = travelRequestDTO.getPause();
        this.kilometers = travelRequestDTO.getKilometers();



    }
    public Travel(Long idUser, Long idScooter, LocalDateTime startDateTime, LocalDateTime finishDateTime, Integer pause, Double kilometers, Price price) {
        this.idUser = idUser;
        this.idScooter = idScooter;
        this.startDateTime = startDateTime;
        this.finishDateTime = finishDateTime;
        this.pause = pause;
        this.kilometers=kilometers;
        this.price=price;

    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getIdScooter() {
        return idScooter;
    }

    public void setIdScooter(Long idScooter) {
        this.idScooter = idScooter;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getFinishDateTime() {
        return finishDateTime;
    }

    public void setFinishDateTime(LocalDateTime finishDateTime) {
        this.finishDateTime = finishDateTime;
    }

    public Integer getPause() {
        return pause;
    }

    public void setPause(Integer pause) {
        this.pause = pause;
    }

    public Double getKilometers() {
        return kilometers;
    }

    public void setKilometers(Double kilometers) {
        this.kilometers = kilometers;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
}
