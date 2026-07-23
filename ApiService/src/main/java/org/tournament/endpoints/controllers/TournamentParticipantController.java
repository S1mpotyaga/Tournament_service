package org.tournament.endpoints.controllers;

import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.tournament.data.dto.TournamentParticipantDTO;
import org.tournament.endpoints.ConverterException;
import org.tournament.endpoints.filters.pageable.IdPaginationFilter;
import org.tournament.endpoints.services.MatchService;
import org.tournament.endpoints.services.TournamentParticipantService;

@Validated
@Slf4j
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

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getAllMatchesByUserIdByFilter(
            @PathVariable("id") @Min(1) Integer userId,
            @RequestParam(value = "pageSize", required = false) @Min(1) Integer pageSize,
            @RequestParam(value = "pageNumber", required = false) @Min(0) Integer pageNumber
    ){
        try {
            log.debug("Вызван метод получения из TournamentParticipantController.getAllMatchesByUserId: id={}", userId);
            var filter = new IdPaginationFilter(userId, pageSize, pageNumber);
            return ResponseEntity.status(HttpStatus.OK).body(matchService.findMatchesByUser(filter));
        } catch (ConverterException e){
            log.error("Ошибка конвертации матчей в DTO", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/tournament/{id}")
    public ResponseEntity<?> getAllUsersByTournamentIdByFilter(
            @PathVariable("id") @Min(1) Integer tournamentId,
            @RequestParam(value = "pageSize", required = false) @Min(1) Integer pageSize,
            @RequestParam(value = "pageNumber", required = false) @Min(0) Integer pageNumber
    ){
        try {
            log.debug("Вызван метод получения из " +
                    "TournamentParticipantController.getAllUsersByTournamentIdByFilter: id={}", tournamentId);
            var filter = new IdPaginationFilter(tournamentId, pageSize, pageNumber);
            return ResponseEntity.status(HttpStatus.OK).body(tpService.getAllUsersByTournamentIdByFilter(filter));
        } catch (ConverterException e){
            log.error("Ошибка конвертации пользователей в DTO={}", tournamentId, e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
