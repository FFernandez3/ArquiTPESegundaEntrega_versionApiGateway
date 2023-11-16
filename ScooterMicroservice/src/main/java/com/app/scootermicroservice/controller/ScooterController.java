package com.app.scootermicroservice.controller;

import com.app.scootermicroservice.Security.jwt.AuthorityConstants;
import com.app.scootermicroservice.domain.Scooter;
import com.app.scootermicroservice.domain.Stop;
import com.app.scootermicroservice.dto.ScooterResponseDTO;
import com.app.scootermicroservice.dto.ScooterRequestDTO;
import com.app.scootermicroservice.dto.TravelResponseDTO;
import com.app.scootermicroservice.service.ScooterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Tuple;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/scooters")
@Tag(name = "ScooterController", description = "Controlador para gestionar recursos relacionados con los Monopatines")
public class ScooterController {
    private final ScooterService scooterService;

    public ScooterController(ScooterService scooterService) {
        this.scooterService = scooterService;
    }

    @PostMapping("")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    @Operation(summary = "Agregar un nuevo monopatín", description = "Este endpoint se utiliza para agregar un nuevo monopatín.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Éxito - Monopatín agregado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScooterResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - Datos del monopatín incorrectos", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
    public ResponseEntity<ScooterResponseDTO> save(@RequestBody ScooterRequestDTO entity) {
        return new ResponseEntity<>( this.scooterService.save(entity), HttpStatus.CREATED );
    }

    @Operation(summary = "Obtener lista de monopatines", description = "Este endpoint se utiliza para obtener una lista de todos los monopatines.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Monopatines obtenidos correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ScooterResponseDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public List<ScooterResponseDTO> findAll( ){
        return this.scooterService.findAll();
    }
    //busca por id
    @GetMapping("/id/{id}")
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    @Operation(summary = "Buscar monopatín por ID", description = "Este endpoint se utiliza para buscar un monopatín por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Monopatín encontrado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScooterResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Monopatín no encontrado", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
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
    @Operation(summary = "Obtener lista de IDs de monopatines", description = "Este endpoint se utiliza para obtener una lista de IDs de monopatines.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Lista de IDs de monopatines obtenida correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Long.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
    public List<Long>getScooterIds(){
        return this.scooterService.getScooterIds();

    }
    //esto debe hacerlo solo el admin
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar monopatín por ID", description = "Este endpoint se utiliza para eliminar un monopatín por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Monopatín eliminado correctamente", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Monopatín no encontrado", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
    @PreAuthorize( "hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
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
    @PreAuthorize( "hasAuthority(\"" + AuthorityConstants.MAINTENANCE + "\" )" )
    @Operation(summary = "Actualizar estado de disponibilidad de un monopatín por ID", description = "Este endpoint se utiliza para actualizar el estado de disponibilidad de un monopatín por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Estado de disponibilidad del monopatín actualizado correctamente", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Monopatín no encontrado", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
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
   @Operation(summary = "Actualizar y obtener los kilómetros recorridos de un monopatín por ID", description = "Este endpoint se utiliza para actualizar los kilómetros recorridos de un monopatín por su ID y obtener el monopatín actualizado.")
   @ApiResponses({
           @ApiResponse(responseCode = "200", description = "Éxito - Kilómetros recorridos actualizados y monopatín obtenido correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScooterResponseDTO.class))),
           @ApiResponse(responseCode = "404", description = "Monopatín no encontrado", content = @Content(mediaType = "text/plain")),
           @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
   })
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
    @Operation(summary = "Actualizar y obtener los tiempos de uso sin pausas del monopatín por ID", description = "Este endpoint se utiliza para actualizar los tiempos sin pausas del monopatín por su ID y obtener el monopatín actualizado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Tiempos de uso continuo actualizados y monopatín obtenido correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScooterResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Monopatín no encontrado", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
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

    @Operation(summary = "Actualizar y obtener los tiempos con pausas del monopatín por ID", description = "Este endpoint se utiliza para actualizar los tiempos con pausas del monopatín por su ID y obtener el monopatín actualizado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Tiempos con pausas actualizados y monopatín obtenido correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScooterResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Monopatín no encontrado", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
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
    @Operation(summary = "Actualizar los kilómetros recorridos de un monopatín por ID", description = "Este endpoint se utiliza para actualizar los kilómetros recorridos de un monopatín por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Kilómetros recorridos actualizados correctamente", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Monopatín no encontrado", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
    public ResponseEntity<?> updateKm(@PathVariable Long id, @RequestParam Double kilometersTraveled){
        int rowsUpdated =this.scooterService.updateKilometersById(id, kilometersTraveled);
        if (rowsUpdated > 0) {
            return new ResponseEntity<>("El scooter fue actualizado con exito." , HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se encontró ningun scooter con el ID proporcionado.", HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/orderByKilometers")
    @Operation(summary = "Obtener monopatines ordenados por kilómetros recorridos", description = "Este endpoint se utiliza para obtener una lista de monopatines ordenados por la cantidad de kilómetros recorridos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Monopatines obtenidos y ordenados correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ScooterResponseDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - URL incorrecta", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
    public List<ScooterResponseDTO> getAllOrderByKm(){
        return this.scooterService.getAllOrderByKm();
    }

    @GetMapping("/orderByTimeWithoutBreaks")
    @Operation(summary = "Obtener monopatines ordenados por tiempo de uso continuo", description = "Este endpoint se utiliza para obtener una lista de monopatines ordenados por el tiempo de uso continuo.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Monopatines obtenidos y ordenados correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ScooterResponseDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - URL incorrecta", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
    public List<ScooterResponseDTO> getAllOrderByTimeWithoutBreaks(){
        return this.scooterService.getAllOrderByTimeWithoutBreaks();
    }

    @GetMapping("/orderByTimeWithBreaks")
    @Operation(summary = "Obtener monopatines ordenados por tiempo de uso con pausas", description = "Este endpoint se utiliza para obtener una lista de monopatines ordenados por el tiempo de uso con pausas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Monopatines obtenidos y ordenados correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ScooterResponseDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - URL incorrecta", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
    public List<ScooterResponseDTO> getAllOrderByTimeWithBreaks(){
        return this.scooterService.getAllOrderByTimeWithBreaks();
    }
    @GetMapping("stopId/{idStop}")
    @Operation(summary = "Obtener monopatines por ID de parada", description = "Este endpoint se utiliza para obtener una lista de monopatines por el ID de la parada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Monopatines obtenidos correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ScooterResponseDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - ID de parada incorrecto", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
    public List<ScooterResponseDTO>getScootersByStop(@PathVariable Stop idStop){
        return this.scooterService.getScootersByStop(idStop);
    }
    @GetMapping("/latitude/{latitude}/longitude/{longitude}")
    @Operation(summary = "Obtener monopatines cercanos a una ubicación", description = "Este endpoint se utiliza para obtener una lista de monopatines cercanos a una ubicación específica (según latitud y longitud).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Monopatines obtenidos correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ScooterResponseDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - Parámetros de ubicación incorrectos", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
    public List<ScooterResponseDTO>getScooterNear(@PathVariable Double latitude, @PathVariable Double longitude){
        return this.scooterService.getScootersNear(latitude, longitude);
    }

    @GetMapping("/travels/{id}")
    @Operation(summary = "Obtener viajes realizados por un monopatín", description = "Este endpoint se utiliza para obtener una lista de viajes realizados por un monopatín específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Viajes obtenidos correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TravelResponseDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - ID de monopatín incorrecto", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "No encontrado - Monopatín no encontrado", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
    public List<TravelResponseDTO>getAllTravelsByScooterId(@PathVariable Long id) throws Exception {
        return this.scooterService.getAllTravels(id);
    }
    // punto A
    @GetMapping("/allUpdated")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstants.MAINTENANCE + "\")" )
    @Operation(summary = "Obtener monopatines con datos actualizados", description = "Este endpoint se utiliza para obtener una lista de monopatines con sus datos (kilómetros y tiempo) actualizados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Monopatines obtenidos correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ScooterResponseDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
    public List<ScooterResponseDTO>getScootersUpdated() throws Exception {
        return this.scooterService.getScootersUpdated();
    }
    // punto E
    @GetMapping("/availabilityQuantity")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstants.ADMIN + "\")" )
    @Operation(summary = "Obtener cantidad de monopatines por disponibilidad", description = "Este endpoint se utiliza para obtener la cantidad de monopatines disponibles y no disponibles.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Cantidad de monopatines obtenida correctamente", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "No encontrado - Monopatines no encontrados", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
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
