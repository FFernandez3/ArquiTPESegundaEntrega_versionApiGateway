package com.app.managementmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StopRequestDTO {
        private Double latitude;
        private Double longitude;

}
