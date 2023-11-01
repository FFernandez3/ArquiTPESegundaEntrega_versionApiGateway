package com.app.managementmicroservice.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@Getter
@Setter
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Integer fileNumber;
    @Column
    private String name;
    @Column
    private String role;


    public void setId(Long id) {
        this.id = id;
    }

    public Manager(Integer fileNumber, String name, String role) {
        this.fileNumber = fileNumber;
        this.name = name;
        this.role = role;
    }


}
