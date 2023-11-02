package com.app.scootermicroservice.domain;
import com.app.scootermicroservice.dto.ScooterRequestDTO;
import com.app.scootermicroservice.dto.StopRequestDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stop implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @OneToMany( mappedBy = "stop", fetch = FetchType.LAZY )
    @JsonManagedReference
    private List<Scooter> scooters;

    public Stop() {
    }

    public Stop(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.scooters = new ArrayList<>();
    }
    public Stop(StopRequestDTO requestDTO) {
        this.latitude = requestDTO.getLatitude();
        this.longitude = requestDTO.getLongitude();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public void addScooter(Scooter s){
        if(!this.scooters.contains(s)){
            this.scooters.add(s);
        }

    }
    public ArrayList<Scooter>getScooters(){
        return new ArrayList<Scooter>(scooters);
    }

}
