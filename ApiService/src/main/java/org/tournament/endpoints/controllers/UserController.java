package org.tournament.endpoints.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.*;
import org.tournament.data.dto.UserDTO;
import org.tournament.endpoints.ConverterException;
import org.tournament.endpoints.services.UserService;

@RestController
@RequestMapping("api/v1/user")
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
}
