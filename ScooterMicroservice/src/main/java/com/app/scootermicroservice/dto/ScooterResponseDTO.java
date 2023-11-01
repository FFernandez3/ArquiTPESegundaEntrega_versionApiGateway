package com.app.scootermicroservice.dto;


import com.app.scootermicroservice.domain.Scooter;
import com.app.scootermicroservice.domain.Stop;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScooterResponseDTO {
    private Long id;
    private Double kilometersTraveled;
    private Double timeWithBreaks;
    private Double timeWithoutBreaks;
    private boolean isAvailable;
    private Double latitude;
    private Double longitude;
    private LocalDate lastMaintenance;
    //cambiar esto a un StopDTO
    private Stop stop;

    public ScooterResponseDTO() {
    }

    public ScooterResponseDTO(Long id, Double kilometersTraveled, Double timeWithBreaks, Double timeWithoutBreaks, boolean isAvailable, Double latitude, Double longitude, LocalDate lastMaintenance, Stop stop) {
        this.id = id;
        this.kilometersTraveled = kilometersTraveled;
        this.timeWithBreaks = timeWithBreaks;
        this.timeWithoutBreaks = timeWithoutBreaks;
        this.isAvailable = isAvailable;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lastMaintenance = lastMaintenance;
        this.stop = stop;
    }

    public ScooterResponseDTO(Scooter scooter ){
        this.id = scooter.getId();
        this.kilometersTraveled = scooter.getKilometersTraveled();
        this.timeWithBreaks = scooter.getTimeWithBreaks();
        this.timeWithoutBreaks = scooter.getTimeWithoutBreaks();
        this.isAvailable = scooter.isAvailable();
        this.latitude = scooter.getLatitude();
        this.longitude = scooter.getLongitude();
        this.lastMaintenance = scooter.getLastMaintenance();
        this.stop=scooter.getStop();
    }
    public ScooterResponseDTO(Long id, Double kilometersTraveled, Double timeWithBreaks, Double timeWithoutBreaks, boolean isAvailable, Double latitude, Double longitude) {
        this.id=id;
        this.kilometersTraveled=kilometersTraveled;
        this.timeWithBreaks=timeWithBreaks;
        this.timeWithoutBreaks=timeWithoutBreaks;
        this.isAvailable = isAvailable;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public ScooterResponseDTO(Long id, Double kilometersTraveled, Double timeWithBreaks, Double timeWithoutBreaks, boolean isAvailable, Double latitude, Double longitude, LocalDate lastMaintenance) {
        this.id=id;
        this.kilometersTraveled=kilometersTraveled;
        this.timeWithBreaks=timeWithBreaks;
        this.timeWithoutBreaks=timeWithoutBreaks;
        this.isAvailable = isAvailable;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lastMaintenance = lastMaintenance;
    }

    public ScooterResponseDTO(Long id, boolean isAvailable, Double latitude, Double longitude, Stop stop) {
        this.id = id;
        this.isAvailable = isAvailable;
        this.latitude = latitude;
        this.longitude = longitude;
        this.stop = stop;
    }

    public ScooterResponseDTO(Long id, Double kilometersTraveled) {
        this.id = id;
        this.kilometersTraveled = kilometersTraveled;
    }

    public ScooterResponseDTO(Long id, Double timeWithBreaks, boolean isAvailable) {
        this.id = id;
        this.timeWithBreaks = timeWithBreaks;
        this.isAvailable = isAvailable;
    }

    public ScooterResponseDTO(Long id, Double timeWithoutBreaks, LocalDate lastMaintenance) {
        this.id = id;
        this.timeWithoutBreaks = timeWithoutBreaks;
        this.lastMaintenance = lastMaintenance;
    }

    public ScooterResponseDTO(boolean isAvailable, Double latitude, Double longitude) {
        this.isAvailable = isAvailable;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public Double getLongitude() {
        return longitude;
    }


    public Double getLatitude() {
        return latitude;
    }


    public Double getTimeWithBreaks() {
        return timeWithBreaks;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
    public Double getTimeWithoutBreaks() {
        return timeWithoutBreaks;
    }

    public Double getKilometersTraveled() {
        return kilometersTraveled;
    }

    public LocalDate getLastMaintenance() {
        return lastMaintenance;
    }

    public Stop getStop() {
        return stop;
    }
}
