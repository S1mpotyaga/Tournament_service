package org.tournament.endpoints.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.*;
import org.tournament.data.dto.MatchDTO;
import org.tournament.endpoints.ConverterException;
import org.tournament.endpoints.filters.MatchSearchFilter;
import org.tournament.endpoints.services.MatchService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/match")
@Slf4j
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

    @GetMapping("/{id}")
    public ResponseEntity<MatchDTO> getMatchById(
            @PathVariable("id") int id
    ){
        try {
            log.info("Вызван метод получения из MatchController.getMatchById: id={}", id);
            MatchDTO matchToGet = matchService.getMatchById(id);
            return ResponseEntity.status(HttpStatus.OK).body(matchToGet);
        } catch (EntityNotFoundException e){
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<MatchDTO>> findAllMatchesByFilter(
            @RequestParam(value = "tournamentId", required = false) Integer id,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber
    ){
        log.info("Вызван метод получения из MatchController.findAllMatchesByFilter");
        var filter = new MatchSearchFilter(id, pageSize, pageNumber);
        return ResponseEntity.status(HttpStatus.OK).body(matchService.getAllMatchByFilter(filter));
    }
}
