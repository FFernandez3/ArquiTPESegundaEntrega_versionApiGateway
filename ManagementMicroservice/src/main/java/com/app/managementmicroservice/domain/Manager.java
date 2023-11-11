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
    @DBRef
    private Set<Authority> authorities;

    /*private String role;*/




   /* public Manager(Integer fileNumber, String name, String role) {
        this.fileNumber = fileNumber;
        this.name = name;
        this.role = role;
    }*/
   public Manager(String name, String email) {
        this.name = name;
        this.email=email;
    }

    public Manager(ManagerRequestDTO managerRequestDTO){
        this.name =managerRequestDTO.getName();
        this.email=managerRequestDTO.getEmail();
    }
    public void setAuthorities( Collection<Authority> authorities ){
        this.authorities = new HashSet<>( authorities );
    }

}
