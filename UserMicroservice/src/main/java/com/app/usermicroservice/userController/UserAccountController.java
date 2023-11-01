package com.app.usermicroservice.userController;

import com.app.usermicroservice.userDomain.Account;
import com.app.usermicroservice.userDomain.User;
import com.app.usermicroservice.userDomain.UserAccount;
import com.app.usermicroservice.userService.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/useraccounts")

public class UserAccountController {
    private final UserAccountService userAccountService;

    public UserAccountController(UserAccountService uas){
        this.userAccountService=uas;
    }

    @PostMapping("")
    public UserAccount save(UserAccount entity) throws Exception{
        return this.userAccountService.save(entity);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<UserAccount> entityDeleted = userAccountService.deleteById(id);

        if (entityDeleted.isPresent()) {
            return ResponseEntity.ok("Entidad eliminada correctamente.");
        } else {
            return ResponseEntity.status(404).body("No se encontró ninguna entidad con el ID proporcionado.");
        }
    }
}
