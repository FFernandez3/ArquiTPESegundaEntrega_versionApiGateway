package com.app.usermicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ScooterDTO {
    private Long id;
    private boolean isAvailable;
    private Double latitude;
    private Double longitude;
    private Stop stop;





}
