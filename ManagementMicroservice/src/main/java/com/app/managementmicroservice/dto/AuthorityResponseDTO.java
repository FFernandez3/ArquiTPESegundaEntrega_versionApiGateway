package com.app.managementmicroservice.dto;

import com.app.managementmicroservice.domain.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityResponseDTO {
    private String name;

    public AuthorityResponseDTO(Authority authority){
        this.name = authority.getName();
    }
}
