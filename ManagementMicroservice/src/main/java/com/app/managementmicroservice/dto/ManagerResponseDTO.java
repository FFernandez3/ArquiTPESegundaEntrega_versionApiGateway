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
    private String name;
    private String email;



    public ManagerResponseDTO(Manager manager){
        this.id = manager.get_id();
        this.name = manager.getName();
        this.email= manager.getEmail();

    }
}
