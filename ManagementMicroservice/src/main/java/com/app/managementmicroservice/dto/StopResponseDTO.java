package com.app.managementmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StopResponseDTO {
    private Long id;
    private Double latitude;
    private Double longitude;

}
