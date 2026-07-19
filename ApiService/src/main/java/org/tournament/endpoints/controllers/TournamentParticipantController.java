package org.tournament.endpoints.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tournament.data.dto.TournamentParticipantDTO;
import org.tournament.endpoints.ConverterException;
import org.tournament.endpoints.services.TournamentParticipantService;

@RestController
@RequestMapping("/api/v1/participants")
public class TournamentParticipantController {
    private final TournamentParticipantService service;

    public TournamentParticipantController(TournamentParticipantService service){
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createParticipant(TournamentParticipantDTO dto){
        try{
            service.createParticipant(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (ConverterException | JpaSystemException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
