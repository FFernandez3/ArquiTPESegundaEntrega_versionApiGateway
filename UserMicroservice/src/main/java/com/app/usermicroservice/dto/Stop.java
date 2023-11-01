package com.app.usermicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor
@Getter
public class Stop {
    private Long id;
    private Double latitude;
    private Double longitude;


}
