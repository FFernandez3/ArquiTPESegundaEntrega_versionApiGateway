package com.app.managementmicroservice.dto;

import com.app.managementmicroservice.domain.Manager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ManagerResponseDTO {

    private String id;

    private Integer fileNumber;

    private String name;

    private String role;

    public ManagerResponseDTO(Manager manager){
        this.id = manager.get_id();
        this.fileNumber = manager.getFileNumber();
        this.name = manager.getName();
        this.role = manager.getRole();
    }
}
