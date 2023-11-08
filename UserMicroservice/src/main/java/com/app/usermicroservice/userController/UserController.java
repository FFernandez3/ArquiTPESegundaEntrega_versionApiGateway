package com.app.usermicroservice.userController;

import com.app.usermicroservice.dto.ScooterDTO;
import com.app.usermicroservice.dto.Stop;
import com.app.usermicroservice.dto.UserDTO;
import com.app.usermicroservice.userDomain.Account;
import com.app.usermicroservice.userDomain.User;
import com.app.usermicroservice.userService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
@Tag(name = "UserController", description = "Controlador para gestionar recursos relacionados con el usuario")
public class UserController {
    private final UserService userService;

    public UserController(UserService us){
        this.userService=us;
    }

    @PostMapping("")
    public User save(User entity) throws Exception{
        return this.userService.save(entity);
    }
    //lista todos sin orden
    @GetMapping("")
    @Operation(summary = "Obtener lista de usuarios", description = "Este endpoint se utiliza para obtener una lista de todos los usuarios.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Usuarios obtenidos correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - URL incorrecta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})
    public List<UserDTO> findAll( ){
        return this.userService.findAll();
    }
    //busca por id
    @GetMapping("/id/{id}")
    @Operation(summary = "Obtener un usuario", description = "Este endpoint se utiliza para obtener un usuario por ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Usuario obtenido correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation= User.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class)))})
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<UserDTO> userFinded=this.userService.findById(id);
        if(userFinded.isPresent()){
            return ResponseEntity.ok(userFinded);
        }
        else {
            return ResponseEntity.status(404).body("No se encontró ningun usuario con el ID proporcionado.");

        }

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un usuario", description = "Este endpoint se utiliza para eliminar un usuario por ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Éxito - Usuario eliminado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation= User.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class)))})
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<User> entityDeleted = userService.deleteById(id);

        if (entityDeleted.isPresent()) {
            return ResponseEntity.ok("Usuario eliminado correctamente.");
        } else {
            return ResponseEntity.status(404).body("No se encontró ningun usuario con el ID proporcionado.");
        }
    }
    @PutMapping("/name/{id}")
    @Operation(summary = "Editar un usuario", description = "Este endpoint se utiliza para editar un usuario por ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Usuario actualizado correctamente", content = @Content(mediaType = "application/json")), /*no pongo esto pq no devulvo un obj , schema = @Schema(implementation= Manager.class)*/
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class)))})
    public ResponseEntity<?> updateNameById(@PathVariable Long id, @RequestParam String name) {
        int rowsUpdated =userService.updateNameById(id, name);
        if (rowsUpdated > 0) {
            return new ResponseEntity<>("El usuario fue actualizado con exito." , HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se encontró ningun usuario con el ID proporcionado.", HttpStatus.NOT_FOUND);
        }

        //Optional <User> entityUpdated=
        /*if (ResponseEntity.ok()){
            return ResponseEntity.ok("El usuario fue actualizado con exito");

        }
        else{
            return ResponseEntity.status(404).body("No se encontró ningun usuario con el ID proporcionado.");
        }*/

    }

    @GetMapping("/scooters/stopId/{idStop}")
    @Operation(summary = "Obtener lista de monopatines por ID de parada", description = "Este endpoint se utiliza para obtener una lista de todos los monopatines que hay en una parada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Monopatines obtenidos correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - URL incorrecta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró ningun monopatin", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class))), /*lo agrego como estamos devolviendo un 404*/
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})
    public List<ScooterDTO> getScootersByStopId(@PathVariable Stop idStop) throws Exception {
        return this.userService.getScootersByStop(idStop);
    }

    @GetMapping("/stops/latitude/{latitude}/longitude/{longitude}")
    @Operation(summary = "Obtener lista de paradas cercanas", description = "Este endpoint se utiliza para obtener una lista de todas las paradas cercanas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Paradas obtenidas correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - URL incorrecta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró ninguna parada", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})
    public List<Stop> getStopsNear(@PathVariable Double latitude, @PathVariable Double longitude) throws Exception {
        return this.userService.getStopsNear(latitude, longitude);
    }

    @GetMapping("/scooters/latitude/{latitude}/longitude/{longitude}")
    @Operation(summary = "Obtener lista de monopatines cercanos", description = "Este endpoint se utiliza para obtener una lista de todos los monopatines cercanos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Monopatines obtenidos correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - URL incorrecta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró ningun monopatin", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})
    public List<ScooterDTO> getScootersNear(@PathVariable Double latitude, @PathVariable Double longitude) throws Exception {
        return this.userService.getScootersNear(latitude, longitude);
    }


}
