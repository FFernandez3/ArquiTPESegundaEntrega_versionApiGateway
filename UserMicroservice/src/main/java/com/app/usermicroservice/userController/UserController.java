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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")

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
    public List<UserDTO> findAll( ){
        return this.userService.findAll();
    }
    //busca por id
    @GetMapping("/id/{id}")
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
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<User> entityDeleted = userService.deleteById(id);

        if (entityDeleted.isPresent()) {
            return ResponseEntity.ok("Usuario eliminado correctamente.");
        } else {
            return ResponseEntity.status(404).body("No se encontró ningun usuario con el ID proporcionado.");
        }
    }
    @PutMapping("/name/{id}")
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
    public List<ScooterDTO> getScootersByStopId(@PathVariable Stop idStop) throws Exception {
        return this.userService.getScootersByStop(idStop);
    }

    @GetMapping("/stops/latitude/{latitude}/longitude/{longitude}")
    public List<Stop> getStopsNear(@PathVariable Double latitude, @PathVariable Double longitude) throws Exception {
        return this.userService.getStopsNear(latitude, longitude);
    }

    @GetMapping("/scooters/latitude/{latitude}/longitude/{longitude}")
    public List<ScooterDTO> getScootersNear(@PathVariable Double latitude, @PathVariable Double longitude) throws Exception {
        return this.userService.getScootersNear(latitude, longitude);
    }


}
