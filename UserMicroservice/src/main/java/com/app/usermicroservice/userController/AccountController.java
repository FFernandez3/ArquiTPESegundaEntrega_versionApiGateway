package com.app.usermicroservice.userController;

import com.app.usermicroservice.dto.AccountDTO;
import com.app.usermicroservice.dto.UserDTO;
import com.app.usermicroservice.userDomain.Account;
import com.app.usermicroservice.userDomain.User;
import com.app.usermicroservice.userService.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/accounts")
@Tag(name = "AccountController", description = "Controlador para gestionar recursos relacionados con la cuenta")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService as){
        this.accountService=as;
    }

    @PostMapping("")
    @Operation(summary = "Agregar una cuenta", description = "Este endpoint se utiliza para agregar una cuenta.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Cuenta agregada correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation= AccountDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - Cuenta no agregada", content = @Content(mediaType = "application/json", schema = @Schema(implementation= ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class)))})
    public Account save(Account entity) throws Exception {
        return this.accountService.save(entity);
    }
    @GetMapping("")
    @Operation(summary = "Obtener lista de cuentas", description = "Este endpoint se utiliza para obtener una lista de todas las cuentas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Cuentas obtenidas correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Account.class)))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - URL incorrecta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})
    public List<AccountDTO> findAll( ){
        return this.accountService.findAll();
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Obtener una cuenta", description = "Este endpoint se utiliza para obtener una cuenta por ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Cuenta obtenida correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation= Account.class))),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class)))})
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<AccountDTO> accountFinded=this.accountService.findById(id);
        if(accountFinded.isPresent()){
            return ResponseEntity.ok(accountFinded);
        }
        else {
            return ResponseEntity.status(404).body("No se encontró ninguna cuenta con el ID proporcionado.");

        }

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una cuenta", description = "Este endpoint se utiliza para eliminar una cuenta por ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Éxito - Cuenta eliminada correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation= Account.class))),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class)))})
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Account> entityDeleted = accountService.deleteById(id);

        if (entityDeleted.isPresent()) {
            return ResponseEntity.ok("Entidad eliminada correctamente.");
        } else {
            return ResponseEntity.status(404).body("No se encontró ninguna cuenta con el ID proporcionado.");
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar una cuenta", description = "Este endpoint se utiliza para editar una cuenta por ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Cuenta actualizada correctamente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class)))})
    public ResponseEntity<?> updateDateById(@PathVariable Long id, @RequestParam LocalDate date) {
        int rowsUpdated=accountService.updateDateById(id, date);
        if (rowsUpdated>0){
            return ResponseEntity.ok("La cuenta fue actualizada con exito");

        }
        else{
            return ResponseEntity.status(404).body("No se encontró ninguna cuenta con el ID proporcionado.");
        }

    }

    @PutMapping("/isCanceled/{id}")
    @Operation(summary = "Editar el estado de una cuenta", description = "Este endpoint se utiliza para editar el estado de una cuenta por ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Estado de la cuenta actualizado correctamente", content = @Content(mediaType = "application/json")), 
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation=ErrorResponse.class)))})
    public ResponseEntity<?> updateIsCanceledById(@PathVariable Long id, @RequestParam boolean isCanceled) {
        int rowsUpdated=accountService.updateIsCanceledById(id, isCanceled);
        if (rowsUpdated>0){
            return ResponseEntity.ok("La cuenta fue actualizada con exito");

        }
        else{
            return ResponseEntity.status(404).body("No se encontró ninguna cuenta con el ID proporcionado.");
        }

    }
    @GetMapping("/ids")
    public List<Long>getIdAccounts(){
        return this.accountService.getIdAccounts();
    }
}
