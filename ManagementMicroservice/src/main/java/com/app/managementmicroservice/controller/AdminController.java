package com.app.managementmicroservice.controller;

import com.app.managementmicroservice.domain.Manager;
import com.app.managementmicroservice.dto.*;
import com.app.managementmicroservice.security.jwt.JwtFilter;
import com.app.managementmicroservice.security.jwt.TokenProvider;
import com.app.managementmicroservice.service.AdminService;
import com.app.managementmicroservice.service.AuthorityService;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("api/employees")
@Tag(name = "AdminController", description = "Controlador para gestionar recursos relacionados con empleados")
/*@ApiResponse(tags = "MyAdminController", description = "Controlador para gestionar recursos relacionados con empleados")*/
public class AdminController {

    private AuthorityService authorityService;
    private AdminService adminService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

/*----------------------------------------------------------AUTENTICACION-----------------------------------*/
@GetMapping("/validate")
public ResponseEntity<ValidateTokenDTO> validateGet() {
    final var employee = SecurityContextHolder.getContext().getAuthentication();
    /*final var authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();*/
    return ResponseEntity.ok(
            ValidateTokenDTO.builder()
                    .username( employee.getName() )
                    .role( employee.getRole() )
                    .isAuthenticated( true )
                    .build()
    );
}
    @Data
    @Builder
    public static class ValidateTokenDTO {
        private boolean isAuthenticated;
        private String username;
        private String role;
    }
    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authenticate( @Valid @RequestBody AuthRequestDTO request ) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken( request.getEmail(), request.getPassword() );
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final var jwt = tokenProvider.createToken (authentication );
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add( JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt );
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }
    /*@PostMapping("/register")
    public ResponseEntity<?> register( @Valid @RequestBody ManagerRequestDTO request ){
        final var newManager = this.adminService.createManager( request );
        return new ResponseEntity<>( newManager, HttpStatus.CREATED );
    }*/

    @PostMapping("/register")
    public ResponseEntity<?> register ( @Valid @RequestBody ManagerRequestDTO request ){
        final var newManager = this.adminService.createManager( request );
        if(newManager!= null){
            return new ResponseEntity<>( newManager, HttpStatus.CREATED );
        }
        return new ResponseEntity<>("Ya existe un administrador con ese email: " + request.getEmail(), HttpStatus.BAD_REQUEST);
    }
    static class JWTToken {
        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
/*----------------------------------------------------------CRUD Y OTROS SERVICIOS---------------------------------*/
    @PostMapping("/authority")
    public ResponseEntity<AuthorityResponseDTO> save(@RequestBody AuthorityRequestDTO entity) {
        return new ResponseEntity<>(this.authorityService.save(entity), HttpStatus.CREATED);
    }
    @GetMapping("/allAuthorities")
    public List<AuthorityResponseDTO> findAllAuthorities(){
        return this.authorityService.findAll();
    }

    @PostMapping("")
    public ManagerResponseDTO save(@RequestBody ManagerRequestDTO entity) throws Exception{
        return this.adminService.save(entity);
    }
    @GetMapping("")
    @Operation(summary = "Obtener lista de empleados", description = "Este endpoint se utiliza para obtener una lista de todos los empleado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Empleados obtenidos correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Manager.class)))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - URL incorrecta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})
    public List<ManagerResponseDTO> findAll( ){
        return this.adminService.findAll();
    }


    //busca por id
    @GetMapping("/id/{id}")
    @Operation(summary = "Obtener un empleado", description = "Este endpoint se utiliza para obtener un empleado por ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Empleado obtenido correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation= Manager.class))),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class)))})

    public ResponseEntity<?> findById(@PathVariable String id){
        Optional<ManagerResponseDTO> managerFinded=this.adminService.findById(id);
        if(managerFinded.isPresent()){
            return ResponseEntity.ok(managerFinded);
        }
        else {
            return ResponseEntity.status(404).body("No se encontró ningun administrador con el ID proporcionado.");

        }

    }
    //esto debe hacerlo solo el admin
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un empleado", description = "Este endpoint se utiliza para eliminar un empleado por ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Éxito - Empleado eliminado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation= Manager.class))),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class)))})
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        Optional<Manager> entityDeleted = adminService.deleteById(id);

        if (entityDeleted.isPresent()) {
            /*return ResponseEntity.ok("Administrador eliminado correctamente.");*/
            return ResponseEntity.status(204).body("Administrador eliminado correctamente."); //VERIFICAR ESTO PQ LO CAMBIAMOS
        } else {
            return ResponseEntity.status(404).body("No se encontró ningun administrador con el ID proporcionado.");
        }
    }

  /*  @PutMapping("/{id}")
    @Operation(summary = "Editar un empleado", description = "Este endpoint se utiliza para editar un empleado por ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Empleado actualizado correctamente", content = @Content(mediaType = "application/json")), /*no pongo esto pq no devulvo un obj , schema = @Schema(implementation= Manager.class)
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class)))})
    public ResponseEntity<?> updateManagerById(@PathVariable Long id, @RequestParam String role) {
        int rowsUpdated =adminService.updateRoleManagerById(id, role);
        if (rowsUpdated > 0){
            return ResponseEntity.ok("El administrador fue actualizado con exito");

        }
        else{
            return ResponseEntity.status(404).body("No se encontró ningun administrador con el ID proporcionado.");
        }

    }*/
    //SERVICIOS QUE HACE EL ADMIN
    @PostMapping("/administrators/scooter")
    @Operation(summary = "Agregar un monopatin", description = "Este endpoint se utiliza para agregar un monopatin.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Monopatin agregado  correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation= ScooterResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - Monopatín no agregado", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class)))})
    public ScooterResponseDTO addScooter(@RequestBody ScooterRequestDTO newScooter) throws Exception{
        return this.adminService.addScooter(newScooter);
    }

    @DeleteMapping("/administrators/scooter/{id}")
    @Operation(summary = "Eliminar un monopatin", description = "Este endpoint se utiliza para eliminar un monopatin por ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Éxito - Monopatin eliminado correctamente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Monopatin no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class)))})
    public ResponseEntity<?> deleteScooterById(@PathVariable Long id) {
        ResponseEntity entityDeleted = adminService.deleteScooterById(id);

        if (entityDeleted != null) {
            return ResponseEntity.ok("Scooter eliminado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningun Scooter con el ID proporcionado.");
        }
    }
    @GetMapping("/administrators/scooters")
    @Operation(summary = "Obtener lista de monopatines", description = "Este endpoint se utiliza para obtener una lista de todos los monopatines.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Monopatines obtenidos correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Manager.class)))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - URL incorrecta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró ningun monopatin", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class))), /*lo agrego como estamos devolviendo un 404*/
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})
    public ResponseEntity<?> getAllScooters() throws Exception {
        List<ScooterResponseDTO> entities = adminService.getAllScooters();
        if(entities!= null){
            return ResponseEntity.ok(entities);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningun Scooter");
        }
    }
    @PostMapping("/administrators/stop")
    @Operation(summary = "Crear una nueva  parada", description = "Este endpoint se utiliza para crear una nueva parada.")
    @ApiResponses({
                    @ApiResponse(responseCode = "201", description = "Parada creada correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StopResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida - Parada no creado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})
    public StopResponseDTO addStop(@RequestBody StopRequestDTO requestDTO){
        return this.adminService.addStop(requestDTO);
    }

    @DeleteMapping("/administrators/stop/{id}")
    @Operation(summary = "Eliminar parada por ID", description = "Este endpoint se utiliza para eliminar una parada por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Parada eliminada correctamente", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Parada no encontrada", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
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
    @Operation(summary = "Modificar estado de cuenta por ID", description = "Este endpoint se utiliza para modificar el estado de una cuenta por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Estado de cuenta modificado correctamente", content = @Content(mediaType = "text/plain")), /*el text plain estará bn aca? no madamos json*/
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada", content = @Content(mediaType = "text/plain", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain", schema = @Schema(implementation = ErrorResponse.class)))
    })
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
    @Operation(summary = "Crear un nuevo precio", description = "Este endpoint se utiliza para crear un nuevo precio.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Precio creado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PriceResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - Precio no creado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})
    public PriceResponseDTO addPrice(@RequestBody PriceRequestDTO requestDTO){
        return this.adminService.addPrice(requestDTO);
    }

    // punto d
    @GetMapping("/administrators/totalInvoiced/month1/{month1}/month2/{month2}/year/{year}")
    @Operation(summary = "Obtener total facturado entre dos meses de un año", description = "Este endpoint se utiliza para obtener el total facturado entre dos meses de un año.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Total facturado obtenido correctamente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - Meses o año incorrectos", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?>getTotalInvoicedByDate(@PathVariable Integer month1, @PathVariable Integer month2, @PathVariable Integer year){
        return this.adminService.getTotalInvoicedByDate(month1, month2, year);
    }
    // punto E
    @GetMapping("/administrators/scootersAvailability")
    @Operation(summary = "Obtener cantidad de monopatines disponibles y no disponibles", description = "Este endpoint se utiliza para obtener la cantidad de monopatines disponibles y no disponibles.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Cantidad de monopatines obtenida correctamente", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
    public ResponseEntity<?>getScootersAvailability(){
        return this.adminService.getScootersAvailability();
    }

    @GetMapping("/administrators/scooters/year/{year}/quantity/{quantity}")
    @Operation(summary = "Obtener cantidad de viajes por monopatín en un año", description = "Este endpoint se utiliza para obtener la cantidad de viajes realizados por cada monopatín en un año específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Lista de viajes obtenida correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - Año o cantidad incorrectos", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
    public ResponseEntity<?>getScootersByYearAndQuantity(@PathVariable Long year,@PathVariable Long quantity) throws Exception {
        return this.adminService.getScootersByYearAndQuantity(year, quantity);
    }
    //SERVICIOS QUE HACE EL DE MANTENIMIENTO
    // punto A
    @GetMapping("/maintenance/scootersReport")
    @Operation(summary = "Obtener informe de monopatines", description = "Este endpoint se utiliza para obtener un informe de monopatines.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Informe de monopatines obtenido correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ScooterResponseDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
    public List<ScooterResponseDTO> getScootersReport() throws Exception {
        return this.adminService.getScootersReport();
    }
    @PutMapping("/maintenance/scooter/id/{id}/availability/{availability}")
    @Operation(summary = "Actualizar disponibilidad de monopatín", description = "Este endpoint se utiliza para enviar o sacar un monopatín de mantenimiento .")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Disponibilidad del monopatín actualizada correctamente", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Monopatín no encontrado", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
    public ResponseEntity<?> sendScooterToMaintenance(@PathVariable Long id, @PathVariable boolean availability){
        ResponseEntity entityDeleted = adminService.sendScooterToMaintenance(id, availability);

        if (entityDeleted != null) {
            return ResponseEntity.ok("La disponibilidad del scooter fue  actualizada correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningun scooter con el ID proporcionado.");
        }
    }

}
