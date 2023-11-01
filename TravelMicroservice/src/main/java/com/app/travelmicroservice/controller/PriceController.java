package com.app.travelmicroservice.controller;

import com.app.travelmicroservice.domain.Price;
import com.app.travelmicroservice.dto.PriceRequestDTO;
import com.app.travelmicroservice.dto.PriceResponseDTO;
import com.app.travelmicroservice.service.PriceService;
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
    public PriceResponseDTO save(@RequestBody PriceRequestDTO requestDTO) throws Exception{
        return this.priceService.save(requestDTO);
    }
    @GetMapping("")
    public List<PriceResponseDTO> findAll( ){
        return this.priceService.findAll();
    }
    //busca por id
    @GetMapping("/id/{id}")
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
