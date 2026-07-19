package org.tournament.endpoints.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.*;
import org.tournament.data.dto.UserDTO;
import org.tournament.endpoints.ConverterException;
import org.tournament.endpoints.filters.UserSearchFilter;
import org.tournament.endpoints.services.UserService;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDTO user){
        try{
            userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (ConverterException | JpaSystemException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable("id") int id
    ){
        try {
            log.info("Вызван метод получения из UserController.getUserById: id={}", id);
            UserDTO userToGet = userService.getUserById(id);
            return ResponseEntity.ok(userToGet);
        } catch (EntityNotFoundException e){
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUserByFilter(
            @RequestParam(value = "userId", required = false) Integer id, // Логики вообще не
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber
    ){
        log.info("Вызван метод получения из UserController.getAllUserByFilter");
        var filter = new UserSearchFilter(id, pageSize, pageNumber);
        return ResponseEntity.ok(userService.searchAllByFilter(filter));
    }
}
