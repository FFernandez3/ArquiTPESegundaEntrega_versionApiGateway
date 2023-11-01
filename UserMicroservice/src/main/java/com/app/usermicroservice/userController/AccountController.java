package com.app.usermicroservice.userController;

import com.app.usermicroservice.dto.AccountDTO;
import com.app.usermicroservice.dto.UserDTO;
import com.app.usermicroservice.userDomain.Account;
import com.app.usermicroservice.userDomain.User;
import com.app.usermicroservice.userService.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/accounts")

public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService as){
        this.accountService=as;
    }

    @PostMapping("")
    public Account save(Account entity) throws Exception {
        return this.accountService.save(entity);
    }
    @GetMapping("")
    public List<AccountDTO> findAll( ){
        return this.accountService.findAll();
    }
    @GetMapping("/id/{id}")
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
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Account> entityDeleted = accountService.deleteById(id);

        if (entityDeleted.isPresent()) {
            return ResponseEntity.ok("Entidad eliminada correctamente.");
        } else {
            return ResponseEntity.status(404).body("No se encontró ninguna cuenta con el ID proporcionado.");
        }
    }

    @PutMapping("/{id}")
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
