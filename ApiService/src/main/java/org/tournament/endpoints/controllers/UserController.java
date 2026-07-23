package org.tournament.endpoints.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.tournament.data.dto.UserDTO;
import org.tournament.endpoints.ConverterException;
import org.tournament.endpoints.filters.pageable.PageableFilter;
import org.tournament.endpoints.services.UserService;

@Validated
@RestController
@RequestMapping("/api/v1/user")
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
    public ResponseEntity<?> getUserById(
            @PathVariable("id") int id
    ){
        try {
            log.debug("Вызван метод получения из UserController.getUserById: id={}", id);
            UserDTO userToGet = userService.getUserById(id);
            return ResponseEntity.ok(userToGet);
        } catch (EntityNotFoundException e){
            log.error("Ошибка с получением пользователя по id", e);
            return ResponseEntity.notFound().build();
        } catch (ConverterException e){
            log.error("Ошибка конвертации в DTO", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllUsers(
            @RequestParam(value = "pageSize", required = false) @Min(1) Integer pageSize,
            @RequestParam(value = "pageNumber", required = false) @Min(0) Integer pageNumber
    ){
        try {
            log.debug("Вызван метод получения из UserController.getAllUserByFilter");
            var filter = new PageableFilter(pageSize, pageNumber);
            return ResponseEntity.ok(userService.getAllUsers(filter));
        } catch (ConverterException e){
            log.error("Ошибка конвертации пользователей в DTO", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
