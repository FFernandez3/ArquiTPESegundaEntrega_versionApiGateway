package com.app.scootermicroservice.controller;

import com.app.scootermicroservice.domain.Stop;
import com.app.scootermicroservice.dto.StopRequestDTO;
import com.app.scootermicroservice.dto.StopResponseDTO;
import com.app.scootermicroservice.service.StopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/stops")
public class StopController {
    private final StopService stopService;

    public StopController(StopService stopService) {
        this.stopService = stopService;
    }
    @PostMapping("")
    public StopResponseDTO save(@RequestBody StopRequestDTO entity) throws Exception{
        return this.stopService.save(entity);
    }
    @GetMapping("")
    public List<StopResponseDTO> findAll( ){
        return this.stopService.findAll();
    }
    //busca por id
    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<StopResponseDTO> stopFinded=this.stopService.findById(id);
        if(stopFinded.isPresent()){
            return ResponseEntity.ok(stopFinded);
        }
        else {
            return ResponseEntity.status(404).body("No se encontró ninguna parada con el ID proporcionado.");

        }

    }
    //esto debe hacerlo solo el admin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Stop> entityDeleted = stopService.deleteById(id);

        if (entityDeleted.isPresent()) {
            return ResponseEntity.ok("Parada eliminado correctamente.");
        } else {
            return ResponseEntity.status(404).body("No se encontró ninguna parada con el ID proporcionado.");
        }
    }
    //esto solo el admin
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLongitudeById(@PathVariable Long id, @RequestBody Double longitude) {
        Optional <Stop> entityUpdated=stopService.updateLongitudeById(id, longitude);
        if (entityUpdated.isPresent()){
            return ResponseEntity.ok("La parada fue actualizada con exito");

        }
        else{
            return ResponseEntity.status(404).body("No se encontró ninguna parada con el ID proporcionado.");
        }

    }
    @GetMapping("/latitude/{latitude}/longitude/{longitude}")
    public List<StopResponseDTO>getStopsNear(@PathVariable Double latitude, @PathVariable Double longitude){
        return this.stopService.getStopsNear(latitude, longitude);

    }
}
