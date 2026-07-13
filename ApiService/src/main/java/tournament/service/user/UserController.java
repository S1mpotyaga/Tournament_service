package tournament.service.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log
@RequestMapping("api/v1/")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("create/user")
    public ResponseEntity<UserDTO> createUser(
            @RequestBody @Valid UserDTO userToCreate
    ){
        log.info("Called create user: user = " + userToCreate);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(userToCreate));
    }

    @GetMapping("get/user/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable("id") Long id
    ){
        log.info("Called get by id method: id = " + id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserById(id));
    }

}
