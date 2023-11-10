package com.app.travelmicroservice.controller;

import com.app.travelmicroservice.domain.Price;
import com.app.travelmicroservice.dto.PriceRequestDTO;
import com.app.travelmicroservice.dto.PriceResponseDTO;
import com.app.travelmicroservice.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/prices")
public class PriceController {
    private final PriceService priceService;


    public PriceController(PriceService priceService) {
        super();
        this.priceService = priceService;
    }

    @PostMapping("")
    @Operation(summary = "Agregar un precio", description = "Este endpoint se utiliza para agregar un nuevo precio.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Creado - Precio agregado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PriceResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - Datos de precio inválidos", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
    public PriceResponseDTO save(@RequestBody PriceRequestDTO requestDTO) throws Exception{
        return this.priceService.save(requestDTO);
    }
    @GetMapping("")
    @Operation(summary = "Obtener todos los precios", description = "Este endpoint se utiliza para obtener una lista de todos los precios.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Precios obtenidos correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PriceResponseDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
    public List<PriceResponseDTO> findAll( ){
        return this.priceService.findAll();
    }
    //busca por id
    @GetMapping("/id/{id}")
    @Operation(summary = "Obtener precio por ID", description = "Este endpoint se utiliza para obtener un precio por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Precio obtenido correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PriceResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado - Precio no encontrado", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<PriceResponseDTO> priceFinded=this.priceService.findById(id);
        if(priceFinded.isPresent()){
            return ResponseEntity.ok(priceFinded);
        }
        else {
            return ResponseEntity.status(404).body("No se encontró ningun precio con el ID proporcionado.");

        }

    }
    //esto debe hacerlo solo el admin
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar precio por ID", description = "Este endpoint se utiliza para eliminar un precio por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Precio eliminado correctamente", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "No encontrado - Precio no encontrado", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Price> entityDeleted = priceService.deleteById(id);

        if (entityDeleted.isPresent()) {
            return ResponseEntity.ok("Precio eliminado correctamente.");
        } else {
            return ResponseEntity.status(404).body("No se encontró ningun Precio con el ID proporcionado.");
        }
    }
    //esto solo el admin punto F
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar tarifa regular por ID de precio", description = "Este endpoint se utiliza para actualizar la tarifa regular por ID de precio.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito - Tarifa regular actualizada correctamente", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "No encontrado - Precio no encontrado", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "text/plain"))
    })
    public ResponseEntity<?> updateRegularFeeById(@PathVariable Long id, @RequestBody Double regularFee) {
        Optional <Price> entityUpdated=priceService.updateRegularFeeById(id, regularFee);
        if (entityUpdated.isPresent()){
            return ResponseEntity.ok("El precio fue actualizado con exito");

        }
        else{
            return ResponseEntity.status(404).body("No se encontró ningun precio con el ID proporcionado.");
        }

    }
}
