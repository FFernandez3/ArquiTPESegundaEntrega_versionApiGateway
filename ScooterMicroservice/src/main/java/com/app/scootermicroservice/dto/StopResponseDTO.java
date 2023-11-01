package com.app.scootermicroservice.dto;

import com.app.scootermicroservice.domain.Scooter;
import com.app.scootermicroservice.domain.Stop;

public class StopResponseDTO {
    private Long id;
    private Double latitude;
    private Double longitude;

    public StopResponseDTO() {
    }
    public StopResponseDTO(Long id, Double latitude, Double longitude) {
        this.id=id;
        this.latitude = latitude;
        this.longitude = longitude;

    }
    public StopResponseDTO(Stop stop ) {
        this.id = stop.getId();
        this.latitude = stop.getLatitude();
        this.longitude = stop.getLongitude();
    }


    public Long getId() {
        return id;
    }

    public Double getLatitude() {
        return latitude;
    }
    public Double getLongitude() {
        return longitude;
    }



}
