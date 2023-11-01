package com.app.managementmicroservice.service;

import com.app.managementmicroservice.domain.Manager;
import com.app.managementmicroservice.dto.ManagerDTO;
import com.app.managementmicroservice.repository.ManagementRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceService {
    private ManagementRepository managementRepository;

    @Transactional
    public Manager save(Manager entity) throws Exception {
        try {
            return this.managementRepository.save(entity);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<ManagerDTO> findAll() {
        return this.managementRepository.findAll().stream()
                .map(manager -> new ManagerDTO(manager.getId(), manager.getFileNumber(),
                        manager.getName(), manager.getRole()))
                .toList();

    }
    public Optional<ManagerDTO> findById(Long id) {
        return this.managementRepository.findById(id).map(manager -> new ManagerDTO(manager.getId(), manager.getFileNumber(),
                manager.getName(), manager.getRole()));

    }
    @Transactional
    public Optional<Manager> deleteById(Long id) {
        Optional<Manager> entityToDelete = managementRepository.findById(id);
        entityToDelete.ifPresent(entity -> managementRepository.deleteById(id));
        return entityToDelete;
    }
    @Transactional
    public int updateRoleManagerById(Long id, String role){
        return this.managementRepository.updateRoleManagerById(id, role);
    }
}
