package com.app.managementmicroservice.controller;

import com.app.managementmicroservice.domain.Manager;
import com.app.managementmicroservice.dto.ManagerDTO;
import com.app.managementmicroservice.service.MaintenanceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("api/technicians")
public class MaintenanceController {
    private MaintenanceService maintenanceService;

    @PostMapping("")
    public Manager save(@RequestBody Manager entity) throws Exception{
        return this.maintenanceService.save(entity);
    }
    @GetMapping("")
    public List<ManagerDTO> findAll( ){
        return this.maintenanceService.findAll();
    }
    //busca por id

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<ManagerDTO> managerFinded=this.maintenanceService.findById(id);
        if(managerFinded.isPresent()){
            return ResponseEntity.ok(managerFinded);
        }
        else {
            return ResponseEntity.status(404).body("No se encontró ningun administrador con el ID proporcionado.");

        }

    }
    //esto debe hacerlo solo el admin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Manager> entityDeleted = maintenanceService.deleteById(id);

        if (entityDeleted.isPresent()) {
            return ResponseEntity.ok("Administrador eliminado correctamente.");
        } else {
            return ResponseEntity.status(404).body("No se encontró ningun administrador con el ID proporcionado.");
        }
    }
    //esto solo el admin
    @PutMapping("/{id}")
    public ResponseEntity<?> updateManagerById(@PathVariable Long id, @RequestParam String role) {
        int rowsUpdated =maintenanceService.updateRoleManagerById(id, role);
        if (rowsUpdated > 0){
            return ResponseEntity.ok("El administrador fue actualizado con exito");

        }
        else{
            return ResponseEntity.status(404).body("No se encontró ningun administrador con el ID proporcionado.");
        }

    }

}
