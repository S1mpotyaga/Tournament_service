package org.tournament.endpoints.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tournament.data.dto.TournamentDTO;
import org.tournament.endpoints.ConverterException;
import org.tournament.endpoints.services.TournamentService;

@RestController
@RequestMapping("/api/v1/tournament")
public class TournamentController {
    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService){
        this.tournamentService = tournamentService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTournament(@RequestBody TournamentDTO tournamentDTO){
        try{
            tournamentService.createTournament(tournamentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(tournamentDTO);
        } catch (ConverterException | JpaSystemException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
