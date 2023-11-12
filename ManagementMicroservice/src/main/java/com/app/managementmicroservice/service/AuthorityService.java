package com.app.managementmicroservice.service;

import com.app.managementmicroservice.domain.Authority;
import com.app.managementmicroservice.dto.AuthorityRequestDTO;
import com.app.managementmicroservice.dto.AuthorityResponseDTO;
import com.app.managementmicroservice.dto.ManagerResponseDTO;
import com.app.managementmicroservice.repository.AuthorityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorityService {
    private final AuthorityRepository authorityRepository;

    public AuthorityResponseDTO save(AuthorityRequestDTO requestDTO){
        var authority = new Authority(requestDTO);
        authority = this.authorityRepository.save(authority);
        return new AuthorityResponseDTO(authority);
    }
    public List<AuthorityResponseDTO> findAll() {
        return this.authorityRepository.findAll().stream()
                .map(authority -> new AuthorityResponseDTO(authority.getName()))
                .toList();

    }
}
