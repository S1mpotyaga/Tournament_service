package org.tournament.endpoints.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tournament.data.dto.MatchDTO;
import org.tournament.endpoints.ConverterException;
import org.tournament.endpoints.services.MatchService;

@RestController
@RequestMapping("/api/v1/match")
public class MatchController {
    private final MatchService matchService;

    public MatchController(MatchService service){
        this.matchService = service;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createMatch(@RequestBody MatchDTO match){
        try {
            matchService.createMatch(match);
            return ResponseEntity.status(HttpStatus.CREATED).body(match);
        } catch (ConverterException | JpaSystemException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
