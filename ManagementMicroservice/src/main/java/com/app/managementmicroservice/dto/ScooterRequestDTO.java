package com.app.managementmicroservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Getter
public class ScooterRequestDTO {
    private boolean isAvailable;
    private Double latitude;
    private Double longitude;
    private Long stopID;
}
