package org.tournament.endpoints.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
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
@Log
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
            log.info("Пришел запрос на получение из UserController.getUserById: " + id);
            UserDTO userToGet = userService.getUserById(id);
            return ResponseEntity.ok(userToGet);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUserByFilter(
            @RequestParam(value = "userId", required = false) Integer id,
            @RequestParam(value = "nick", required = false) String nick,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber
    ){
        log.info("Пришел запрос на получение из UserController.getAllUserByFilter");
        var filter = new UserSearchFilter(id, nick, pageSize, pageNumber);
        return ResponseEntity.ok(userService.searchAllByFilter(filter));
    }
}
