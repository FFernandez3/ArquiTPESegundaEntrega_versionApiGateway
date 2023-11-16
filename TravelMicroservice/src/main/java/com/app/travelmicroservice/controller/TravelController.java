package com.app.travelmicroservice.controller;

import com.app.travelmicroservice.domain.Travel;
import com.app.travelmicroservice.dto.TravelRequestDTO;
import com.app.travelmicroservice.dto.TravelResponseDTO;
import com.app.travelmicroservice.security.jwt.AuthorityConstants;
import com.app.travelmicroservice.service.TravelService;
import jakarta.persistence.Tuple;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/travels")
public class TravelController {
    private final TravelService travelService;



    public TravelController(TravelService travelService) {
        super();
        this.travelService = travelService;

    }

    @PostMapping("")
    public ResponseEntity<TravelResponseDTO> save(@RequestBody TravelRequestDTO entity) throws Exception{
        return new ResponseEntity<>(this.travelService.save(entity), HttpStatus.CREATED);
    }

    @GetMapping("")
    @PreAuthorize( "hasAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public List<TravelResponseDTO> findAll( ){
        return this.travelService.findAll();
    }
    //busca por id
    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<TravelResponseDTO> travelFinded=this.travelService.findById(id);
        if(travelFinded.isPresent()){
            return ResponseEntity.ok(travelFinded);
        }
        else {
            return ResponseEntity.status(404).body("No se encontró ningun viaje con el ID proporcionado.");

        }

    }
    //esto debe hacerlo solo el admin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Travel> entityDeleted = travelService.deleteById(id);

        if (entityDeleted.isPresent()) {
            return ResponseEntity.ok("Viaje eliminado correctamente.");
        } else {
            return ResponseEntity.status(404).body("No se encontró ningun viaje con el ID proporcionado.");
        }
    }
    //esto solo el admin
    @PutMapping("scooterId/{id}")
    public ResponseEntity<?> updateScooterById(@PathVariable Long id, @RequestParam Long idScooter) {
        int rowsUpdated=travelService.updateScooterById(id, idScooter);
        if (rowsUpdated>0){
            return new ResponseEntity<>("El viaje fue actualizado con exito." , HttpStatus.OK);

        }
        else{
            return new ResponseEntity<>("No se encontró ningun viaje con el ID proporcionado.", HttpStatus.NOT_FOUND);
        }

    }


    @GetMapping("/byScooterId/{id}")
    public List<TravelResponseDTO> getTravelsByScooterId(@PathVariable Long id){
        return this.travelService.getTravelsByScooterId(id);
    }

    @PutMapping("finishDateTime/{id}")
    public ResponseEntity<?> updateFinishDateTimeById(@PathVariable Long id, @RequestParam LocalDateTime finishDateTime) {
        int rowsUpdated=travelService.updateFinishDateTimeById(id, finishDateTime);
        if (rowsUpdated>0){
            return new ResponseEntity<>("El viaje fue actualizado con exito." , HttpStatus.OK);

        }
        else{
            return new ResponseEntity<>("No se encontró ningun viaje con el ID proporcionado.", HttpStatus.NOT_FOUND);
        }

    }
    //consigna D
    @PreAuthorize( "hasAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    @GetMapping("invoiced/month1/{month1}/month2/{month2}/year/{year}")
    public  ResponseEntity<?> getTotalInvoicedByDate(@PathVariable Integer month1, @PathVariable Integer month2, @PathVariable Integer year){

        Double invoiced= this.travelService.getTotalInvoicedByDate(month1, month2, year);
        if (invoiced!=0.0 && (month1<month2)){
            return ResponseEntity.ok("El total facturado entre"+month1+" y "+month2+" de "+year+" es "+invoiced);
        }
        else if(invoiced!=0.0 && (month1>month2)){
            return ResponseEntity.ok("El total facturado entre"+month2+" y "+month1+" de "+year+" es "+invoiced);
        }
        else if (invoiced==0.0) {
            return ResponseEntity.ok("El total facturado fue de "+invoiced);

        }
        else {
            return ResponseEntity.status(404).body("No se encontró ningun viaje con los parametros otorgados");

        }



    }
    @GetMapping("/totalCost/{id}")
    public Double getTotalCost(@PathVariable Long id){
        return this.travelService.getTotalCost(id);
    }

    @GetMapping("/allByYear/{year}/quantity/{quantity}")
    @PreAuthorize( "hasAuthority(\"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?>getTravelsByYear(@PathVariable Long year, @PathVariable Long quantity) {
        List<Tuple> results = travelService.getTravelsByYear(year, quantity);
        if (results != null) {
            List<String> responseMessage = new ArrayList<>();
            for(int i = 0; i < results.size(); i++) {
                Integer yearResult = results.get(i).get("yearResult", Integer.class);
                Long idScooter = results.get(i).get("id", Long.class);
                Long tripCount = results.get(i).get("trip", Long.class);

                String message = "En el año " + yearResult +
                        " el monopatin " + idScooter + " hizo " + tripCount + " viajes";
                responseMessage.add(message);

            }
            return ResponseEntity.ok(responseMessage);
        } else {
            return ResponseEntity.status(404).body("No se encontró ningun viaje.");
        }
    }



}
