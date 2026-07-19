package org.tournament.endpoints.controllers;

import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.*;
import org.tournament.data.dto.MatchDTO;
import org.tournament.data.dto.TournamentParticipantDTO;
import org.tournament.endpoints.ConverterException;
import org.tournament.endpoints.filters.TournamentParticipantFilter;
import org.tournament.endpoints.services.MatchService;
import org.tournament.endpoints.services.TournamentParticipantService;

import java.util.List;

@Log
@RestController
@RequestMapping("/api/v1/participants")
public class TournamentParticipantController {
    private final TournamentParticipantService tpService;
    private final MatchService matchService;

    public TournamentParticipantController(
            TournamentParticipantService tpService,
            MatchService matchService){
        this.tpService = tpService;
        this.matchService = matchService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createParticipant(TournamentParticipantDTO dto){
        try{
            tpService.createParticipant(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (ConverterException | JpaSystemException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("user/{id}")
    public ResponseEntity<List<MatchDTO>> getAllMatchesByUserIdByFilter(
            @PathVariable("id") int userId,
            @RequestParam(value = "tournamentId", required = false) Integer tournamentId,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber

    ){
        log.info("Вызван метод получения из TournamentParticipantController.getAllMatchesByUserId");
        var filter = new TournamentParticipantFilter(userId, tournamentId, pageSize, pageNumber);
        return ResponseEntity.status(HttpStatus.OK).body(matchService.getAllMatchesByUserIdByFilter(filter));
    }




}
