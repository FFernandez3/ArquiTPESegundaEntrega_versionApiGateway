package com.app.scootermicroservice.domain;

import com.app.scootermicroservice.dto.ScooterRequestDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Scooter  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double kilometersTraveled;

    @Column(nullable = true)
    private Double timeWithBreaks;

    @Column(nullable = true)
    private Double timeWithoutBreaks;

    @Column
    private boolean isAvailable;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column(nullable = true)
    private LocalDate lastMaintenance;

    @ManyToOne(fetch=FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(nullable = false)
    private Stop stop;

    public Scooter() {
    }

    public Scooter(boolean isAvailable, Double latitude, Double longitude, Stop stop) {
        this.isAvailable = isAvailable;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lastMaintenance = null;
        this.stop=stop;
    }

    public Scooter(boolean isAvailable, Double latitude, Double longitude) {
        this.isAvailable = isAvailable;
        this.latitude = latitude;
        this.longitude = longitude;
    }
//este usa el save
    public Scooter(ScooterRequestDTO requestDTO) {
        this.isAvailable = requestDTO.isAvailable();
        this.latitude = requestDTO.getLatitude();
        this.longitude = requestDTO.getLongitude();
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getKilometersTraveled() {
        return kilometersTraveled;
    }

    public void setKilometersTraveled(Double kilometersTraveled) {
        this.kilometersTraveled += kilometersTraveled;
    }

    public Double getTimeWithBreaks() {
        return timeWithBreaks;
    }

    public void setTimeWithBreaks(Double timeWithBreaks) {
        this.timeWithBreaks += timeWithBreaks;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Double getTimeWithoutBreaks() {
        return timeWithoutBreaks;
    }

    public void setTimeWithoutBreaks(Double timeWithoutBreaks) {
        this.timeWithoutBreaks = timeWithoutBreaks;
    }

    public LocalDate getLastMaintenance() {
        return lastMaintenance;
    }

    public void setLastMaintenance(LocalDate lastMaintenance) {
        this.lastMaintenance = lastMaintenance;
    }

    public Stop getStop() {
        return this.stop;
    }

    public void setStop(Stop stop) {
        this.stop = stop;
    }
}
