package com.app.managementmicroservice.domain;

import com.app.managementmicroservice.dto.ManagerRequestDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Document
@Data
@NoArgsConstructor
public class Manager {
    @Id
    private String _id;
    private String email;
    private String password;
    private String name;
    private String role;

    /*private String role;*/




   /* public Manager(Integer fileNumber, String name, String role) {
        this.fileNumber = fileNumber;
        this.name = name;
        this.role = role;
    }*/
   public Manager(String name, String email, String role) {
        this.name = name;
        this.email=email;
        this.role = role;
    }

    public Manager(ManagerRequestDTO managerRequestDTO){
        this.name =managerRequestDTO.getName();
        this.email=managerRequestDTO.getEmail();
        this.role = managerRequestDTO.getRole();
    }


}
