package org.tournament.endpoints.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.*;
import org.tournament.data.dto.TournamentDTO;
import org.tournament.endpoints.ConverterException;
import org.tournament.endpoints.services.TournamentService;

@RestController
@RequestMapping("/api/v1/tournament")
@Slf4j
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

    @GetMapping("/{id}")
    public ResponseEntity<TournamentDTO> getTournamentById(
            @PathVariable("id") int id
    ){
        try {
            log.info("Вызван метод получения из TournamentController.getTournamentById");
            TournamentDTO tournamentDTO = tournamentService.getTournamentById(id);
            return ResponseEntity.status(HttpStatus.OK).body(tournamentDTO);
        } catch (EntityNotFoundException e){
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
