package com.app.managementmicroservice.controller;

import com.app.managementmicroservice.dto.*;
import com.app.managementmicroservice.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/administrators")
public class AdminController {

    private AdminService adminService;

    @PostMapping("/scooter")
    public ScooterResponseDTO addScooter(@RequestBody ScooterRequestDTO newScooter) throws Exception{
        return this.adminService.addScooter(newScooter);
    }

    @DeleteMapping("/scooter/{id}")
    public ResponseEntity<?> deleteScooterById(@PathVariable Long id) {
        ResponseEntity entityDeleted = adminService.deleteScooterById(id);

        if (entityDeleted != null) {
            return ResponseEntity.ok("Scooter eliminado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningun Scooter con el ID proporcionado.");
        }
    }
    @GetMapping("/allScooters")
    public ResponseEntity<?> getAllScooters() throws Exception {
        List<ScooterResponseDTO> entities = adminService.getAllScooters();
        if(entities!= null){
            return ResponseEntity.ok(entities);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningun Scooter");
        }
    }
    @PostMapping("/stop")
    public StopResponseDTO addStop(@RequestBody StopRequestDTO requestDTO){
        return this.adminService.addStop(requestDTO);
    }

    @DeleteMapping("/stop/{id}")
    public ResponseEntity<?> deleteStopById(@PathVariable Long id) {
        ResponseEntity entityDeleted = adminService.deleteStopById(id);

        if (entityDeleted != null) {
            return ResponseEntity.ok("Parada eliminado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ninguna parada con el ID proporcionado.");
        }
    }
    // punto B
    @PutMapping("/modifyStateOfAccount/{id}/state/{state}")
    public ResponseEntity<?> modifyStateOfAccountById(@PathVariable Long id,@PathVariable boolean state) {
        ResponseEntity entityDeleted = adminService.modifyStateOfAccountById(id, state);

        if (entityDeleted != null) {
            return ResponseEntity.ok("Estado de cuenta actualizado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ninguna cuenta con el ID proporcionado.");
        }
    }

    //Punto F
    @PostMapping("/price")
    public PriceResponseDTO addPrice(@RequestBody PriceRequestDTO requestDTO){
        return this.adminService.addPrice(requestDTO);
    }
    // punto A
    @GetMapping("/maintenance/scootersReport")
    public List<ScooterResponseDTO> getScootersReport() throws Exception {
        return this.adminService.getScootersReport();
    }
    // punto D
    @GetMapping("/totalInvoiced/month1/{month1}/month2/{month2}/year/{year}")
    public ResponseEntity<?>getTotalInvoicedByDate(@PathVariable Integer month1, @PathVariable Integer month2, @PathVariable Integer year){
        return this.adminService.getTotalInvoicedByDate(month1, month2, year);
    }
    // punto E
    @GetMapping("/scootersAvailability")
    public ResponseEntity<?>getScootersAvailability(){
        return this.adminService.getScootersAvailability();
    }

    @GetMapping("/scooters/year/{year}/quantity/{quantity}")
    public ResponseEntity<?>getScootersByYearAndQuantity(@PathVariable Long year,@PathVariable Long quantity) throws Exception {
        return this.adminService.getScootersByYearAndQuantity(year, quantity);
    }

}
