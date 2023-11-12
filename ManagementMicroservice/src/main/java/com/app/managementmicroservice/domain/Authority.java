package com.app.managementmicroservice.domain;

import com.app.managementmicroservice.dto.AuthorityRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Authority  {

    @Id
    private String name;

    public Authority(AuthorityRequestDTO requestDTO){
        this.name = requestDTO.getName();
    }
}
