package com.app.managementmicroservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@AllArgsConstructor
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
    private StopResponseDTO stop;

    public ScooterResponseDTO(boolean isAvailable, Double latitude, Double longitude, StopResponseDTO stop) {
        this.isAvailable = isAvailable;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lastMaintenance = null;
        this.stop=stop;
    }

    public ScooterResponseDTO(boolean isAvailable, Double latitude, Double longitude) {
        this.isAvailable = isAvailable;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    
}
