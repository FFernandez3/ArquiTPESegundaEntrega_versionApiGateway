package com.app.managementmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ManagerRequestDTO {


    private String name;
    private String email;
    private String password;
    private Set<String> authorities;


}
