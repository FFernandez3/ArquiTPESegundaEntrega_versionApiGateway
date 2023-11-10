package com.app.managementmicroservice.domain;

import com.app.managementmicroservice.dto.ManagerRequestDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
public class Manager {
    @Id
    private String _id;

    private Integer fileNumber;

    private String name;

    private String role;



    public Manager(Integer fileNumber, String name, String role) {
        this.fileNumber = fileNumber;
        this.name = name;
        this.role = role;
    }

    public Manager(ManagerRequestDTO managerRequestDTO){
        this.fileNumber = managerRequestDTO.getFileNumber();
        this.name =managerRequestDTO.getName();
        this.role = managerRequestDTO.getRole();
    }

}
