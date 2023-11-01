package com.app.scootermicroservice.controller;

import com.app.scootermicroservice.domain.Scooter;
import com.app.scootermicroservice.domain.Stop;
import com.app.scootermicroservice.dto.ScooterResponseDTO;
import com.app.scootermicroservice.dto.ScooterRequestDTO;
import com.app.scootermicroservice.dto.TravelResponseDTO;
import com.app.scootermicroservice.service.ScooterService;
import jakarta.persistence.Tuple;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/scooters")
public class ScooterController {
    private final ScooterService scooterService;

    public ScooterController(ScooterService scooterService) {
        this.scooterService = scooterService;
    }

    @PostMapping("")
    public ResponseEntity<ScooterResponseDTO> save(@RequestBody ScooterRequestDTO entity) {
        return new ResponseEntity<>( this.scooterService.save(entity), HttpStatus.CREATED );
    }
    @GetMapping("")
    public List<ScooterResponseDTO> findAll( ){
        return this.scooterService.findAll();
    }
    //busca por id
    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<ScooterResponseDTO> scooterFinded=this.scooterService.findById(id);
        if(scooterFinded.isPresent()){
            return ResponseEntity.ok(scooterFinded);
        }
        else {
            return ResponseEntity.status(404).body("No se encontró ningun scooter con el ID proporcionado.");

        }

    }
    @GetMapping("/ids")
    public List<Long>getScooterIds(){
        return this.scooterService.getScooterIds();

    }
    //esto debe hacerlo solo el admin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Scooter> entityDeleted = scooterService.deleteById(id);

        if (entityDeleted.isPresent()) {
            return ResponseEntity.ok("Scooter eliminado correctamente.");
        } else {
            return ResponseEntity.status(404).body("No se encontró ningun scooter con el ID proporcionado.");
        }
    }
    //esto solo el admin
    @PutMapping("/isAvailable/{id}")
    public ResponseEntity<?> updateIsAvailableById(@PathVariable Long id, @RequestParam boolean isAvailable) {
        int rowsUpdated=scooterService.updateIsAvailableById(id, isAvailable);
        if (rowsUpdated>0){
            return ResponseEntity.ok("El scooter fue actualizado con exito");

        }
        else{
            return ResponseEntity.status(404).body("No se encontró ningun scooter con el ID proporcionado.");
        }

    }
   @GetMapping("totalKilometers/{id}")
    public ResponseEntity<?> setKilometers(@PathVariable Long id) throws Exception{
        Optional<ScooterResponseDTO> entityUpdated=scooterService.setKilometers(id);
        if (entityUpdated.isPresent()){
            return ResponseEntity.ok(entityUpdated);
        }
        else {
            return ResponseEntity.status(404).body("No se encontró ningun scooter con el ID proporcionado.");

        }
    }

    @GetMapping("totalTimeWithoutBreaks/{id}")
    public ResponseEntity<?> setTimeWithoutBreaks(@PathVariable Long id) throws Exception{
        Optional<ScooterResponseDTO> entityUpdated=scooterService.setTimeWithoutBreaks(id);
        if (entityUpdated.isPresent()){
            return ResponseEntity.ok(entityUpdated);
        }
        else {
            return ResponseEntity.status(404).body("No se encontró ningun scooter con el ID proporcionado.");

        }
    }

    @GetMapping("totalTimeWithBreaks/{id}")
    public ResponseEntity<?> setTimeWithBreaks(@PathVariable Long id) throws Exception{
        Optional<ScooterResponseDTO> entityUpdated=scooterService.setTimeWithBreaks(id);
        if (entityUpdated.isPresent()){
            return ResponseEntity.ok(entityUpdated);
        }
        else {
            return ResponseEntity.status(404).body("No se encontró ningun scooter con el ID proporcionado.");

        }
    }
    // para debbug
    @PutMapping("/km/{id}")
    public ResponseEntity<?> updateKm(@PathVariable Long id, @RequestParam Double kilometersTraveled){
        int rowsUpdated =this.scooterService.updateKilometersById(id, kilometersTraveled);
        if (rowsUpdated > 0) {
            return new ResponseEntity<>("El scooter fue actualizado con exito." , HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se encontró ningun scooter con el ID proporcionado.", HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/orderByKilometers")
    public List<ScooterResponseDTO> getAllOrderByKm(){
        return this.scooterService.getAllOrderByKm();
    }

    @GetMapping("/orderByTimeWithoutBreaks")
    public List<ScooterResponseDTO> getAllOrderByTimeWithoutBreaks(){
        return this.scooterService.getAllOrderByTimeWithoutBreaks();
    }

    @GetMapping("/orderByTimeWithBreaks")
    public List<ScooterResponseDTO> getAllOrderByTimeWithBreaks(){
        return this.scooterService.getAllOrderByTimeWithBreaks();
    }
    @GetMapping("stopId/{idStop}")
    public List<ScooterResponseDTO>getScootersByStop(@PathVariable Stop idStop){
        return this.scooterService.getScootersByStop(idStop);
    }
    @GetMapping("/latitude/{latitude}/longitude/{longitude}")
    public List<ScooterResponseDTO>getScooterNear(@PathVariable Double latitude, @PathVariable Double longitude){
        return this.scooterService.getScootersNear(latitude, longitude);
    }
    @GetMapping("/travels/{id}")
    public List<TravelResponseDTO>getAllTravelsByScooterId(@PathVariable Long id) throws Exception {
        return this.scooterService.getAllTravels(id);
    }
    // punto A
    @GetMapping("/allUpdated")
    public List<ScooterResponseDTO>getScootersUpdated() throws Exception {
        return this.scooterService.getScootersUpdated();
    }
    // punto E
    @GetMapping("/availabilityQuantity")
    public ResponseEntity<?> getQuantityScootersByAvailability(){
        Tuple results=scooterService.getQuantityScootersAvailability();
        if (results != null){
            Long unavailableCount = results.get("unavailable_count", Long.class);
            Long availableCount = results.get("available_count", Long.class);

            String responseMessage = "La cantidad de monopatines disponibles es de " + availableCount +
                    " y los monopatines no disponibles son " + unavailableCount;
            return ResponseEntity.ok(responseMessage);
        }
        else {
            return ResponseEntity.status(404).body("No se encontró ningun scooter.");

        }
    }

}
