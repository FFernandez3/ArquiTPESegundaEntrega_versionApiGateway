package com.app.scootermicroservice.controller;

import com.app.scootermicroservice.domain.Stop;
import com.app.scootermicroservice.dto.StopRequestDTO;
import com.app.scootermicroservice.dto.StopResponseDTO;
import com.app.scootermicroservice.service.StopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
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

    @Operation(summary = "Agregar una parada", description = "Este endpoint se utiliza para agregar una nueva parada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Parada agregada correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StopResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - Error en los datos de la parada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public StopResponseDTO save(@RequestBody StopRequestDTO entity) throws Exception{
        return this.stopService.save(entity);
    }
    @GetMapping("")
    @Operation(summary = "Obtener todas las paradas", description = "Este endpoint se utiliza para obtener una lista de todas las paradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Paradas obtenidas correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StopResponseDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public List<StopResponseDTO> findAll( ){
        return this.stopService.findAll();
    }
    //busca por id
    @GetMapping("/id/{id}")
    @Operation(summary = "Obtener una parada por ID", description = "Este endpoint se utiliza para obtener una parada por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Parada obtenida correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StopResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado - Parada no encontrada", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
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
    @Operation(summary = "Eliminar una parada por ID", description = "Este endpoint se utiliza para eliminar una parada por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Parada eliminada correctamente", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "No encontrado - Parada no encontrada", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
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
    @Operation(summary = "Actualizar longitud de una parada por ID", description = "Este endpoint se utiliza para actualizar la longitud de una parada por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Parada actualizada correctamente", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "No encontrado - Parada no encontrada", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
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
    @Operation(summary = "Obtener paradas cercanas a una latitud y longitud", description = "Este endpoint se utiliza para obtener una lista de paradas cercanas a una latitud y longitud dadas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Paradas obtenidas correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StopResponseDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - Latitud y/o longitud inválidas", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
    public List<StopResponseDTO>getStopsNear(@PathVariable Double latitude, @PathVariable Double longitude){
        return this.stopService.getStopsNear(latitude, longitude);

    }
}
