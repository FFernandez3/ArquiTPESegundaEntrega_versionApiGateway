package com.app.managementmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ManagerRequestDTO {
    private Integer fileNumber;

    private String name;

    private String role;
}
