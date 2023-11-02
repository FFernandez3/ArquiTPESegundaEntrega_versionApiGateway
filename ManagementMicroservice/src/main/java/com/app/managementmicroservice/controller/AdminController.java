package com.app.managementmicroservice.controller;

import com.app.managementmicroservice.domain.Manager;
import com.app.managementmicroservice.dto.*;
import com.app.managementmicroservice.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("api/employees")
public class AdminController {

    private AdminService adminService;

    @PostMapping("")
    public Manager save(@RequestBody Manager entity) throws Exception{
        return this.adminService.save(entity);
    }
    @GetMapping("")
    public List<ManagerDTO> findAll( ){
        return this.adminService.findAll();
    }

    //busca por id
    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<ManagerDTO> managerFinded=this.adminService.findById(id);
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
        Optional<Manager> entityDeleted = adminService.deleteById(id);

        if (entityDeleted.isPresent()) {
            return ResponseEntity.ok("Administrador eliminado correctamente.");
        } else {
            return ResponseEntity.status(404).body("No se encontró ningun administrador con el ID proporcionado.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateManagerById(@PathVariable Long id, @RequestParam String role) {
        int rowsUpdated =adminService.updateRoleManagerById(id, role);
        if (rowsUpdated > 0){
            return ResponseEntity.ok("El administrador fue actualizado con exito");

        }
        else{
            return ResponseEntity.status(404).body("No se encontró ningun administrador con el ID proporcionado.");
        }

    }
    //SERVICIOS QUE HACE EL ADMIN
    @PostMapping("/administrators/scooter")
    public ScooterResponseDTO addScooter(@RequestBody ScooterRequestDTO newScooter) throws Exception{
        return this.adminService.addScooter(newScooter);
    }

    @DeleteMapping("/administrators/scooter/{id}")
    public ResponseEntity<?> deleteScooterById(@PathVariable Long id) {
        ResponseEntity entityDeleted = adminService.deleteScooterById(id);

        if (entityDeleted != null) {
            return ResponseEntity.ok("Scooter eliminado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningun Scooter con el ID proporcionado.");
        }
    }
    @GetMapping("/administrators/scooters")
    public ResponseEntity<?> getAllScooters() throws Exception {
        List<ScooterResponseDTO> entities = adminService.getAllScooters();
        if(entities!= null){
            return ResponseEntity.ok(entities);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningun Scooter");
        }
    }
    @PostMapping("/administrators/stop")
    public StopResponseDTO addStop(@RequestBody StopRequestDTO requestDTO){
        return this.adminService.addStop(requestDTO);
    }

    @DeleteMapping("/administrators/stop/{id}")
    public ResponseEntity<?> deleteStopById(@PathVariable Long id) {
        ResponseEntity entityDeleted = adminService.deleteStopById(id);

        if (entityDeleted != null) {
            return ResponseEntity.ok("Parada eliminado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ninguna parada con el ID proporcionado.");
        }
    }
    // punto B
    @PutMapping("/administrators/modifyStateOfAccount/{id}/state/{state}")
    public ResponseEntity<?> modifyStateOfAccountById(@PathVariable Long id,@PathVariable boolean state) {
        ResponseEntity entityDeleted = adminService.modifyStateOfAccountById(id, state);

        if (entityDeleted != null) {
            return ResponseEntity.ok("Estado de cuenta actualizado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ninguna cuenta con el ID proporcionado.");
        }
    }

    //Punto F
    @PostMapping("/administrators/price")
    public PriceResponseDTO addPrice(@RequestBody PriceRequestDTO requestDTO){
        return this.adminService.addPrice(requestDTO);
    }

    // punto d
    @GetMapping("/administrators/totalInvoiced/month1/{month1}/month2/{month2}/year/{year}")
    public ResponseEntity<?>getTotalInvoicedByDate(@PathVariable Integer month1, @PathVariable Integer month2, @PathVariable Integer year){
        return this.adminService.getTotalInvoicedByDate(month1, month2, year);
    }
    // punto E
    @GetMapping("/administrators/scootersAvailability")
    public ResponseEntity<?>getScootersAvailability(){
        return this.adminService.getScootersAvailability();
    }

    @GetMapping("/administrators/scooters/year/{year}/quantity/{quantity}")
    public ResponseEntity<?>getScootersByYearAndQuantity(@PathVariable Long year,@PathVariable Long quantity) throws Exception {
        return this.adminService.getScootersByYearAndQuantity(year, quantity);
    }
    //SERVICIOS QUE HACE EL DE MANTENIMIENTO
    // punto A
    @GetMapping("/maintenance/scootersReport")
    public List<ScooterResponseDTO> getScootersReport() throws Exception {
        return this.adminService.getScootersReport();
    }
    @PutMapping("/maintenance/scooter/id/{id}/availability/{availability}")
    public ResponseEntity<?> sendScooterToMaintenance(@PathVariable Long id, @PathVariable boolean availability){
        ResponseEntity entityDeleted = adminService.sendScooterToMaintenance(id, availability);

        if (entityDeleted != null) {
            return ResponseEntity.ok("La disponibilidad del scooter fue  actualizada correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningun scooter con el ID proporcionado.");
        }
    }

}
