package org.tournament.endpoints.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.tournament.data.dto.MatchDTO;
import org.tournament.endpoints.ConverterException;
import org.tournament.endpoints.filters.pageable.PageableFilter;
import org.tournament.endpoints.services.MatchService;

@Validated
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
    public ResponseEntity<?> getMatchById(
            @PathVariable("id") int id
    ){
        try {
            log.debug("Вызван метод получения из MatchController.getMatchById: id={}", id);
            MatchDTO matchToGet = matchService.getMatchById(id);
            return ResponseEntity.status(HttpStatus.OK).body(matchToGet);
        } catch (EntityNotFoundException e){
            log.error("Ошибка с получением матча по id", e);
            return ResponseEntity.notFound().build();
        } catch (ConverterException e){
            log.error("Ошибка конвертации матча в DTO", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllMatchesByFilter(
            @RequestParam(value = "pageSize", required = false) @Min(1) Integer pageSize,
            @RequestParam(value = "pageNumber", required = false) @Min(0) Integer pageNumber

    ){
        log.debug("Вызван метод получения из MatchController.findAllMatchesByFilter");
        var filter = new PageableFilter(pageSize, pageNumber);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(matchService.findAllMatches(filter));
        } catch (ConverterException e){
            log.error("Ошибка конвертации матчей в DTO", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
