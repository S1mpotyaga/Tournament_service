package org.tournament.endpoints.services;

import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.tournament.data.dto.MatchDTO;
import org.tournament.data.entity.MatchEntity;
import org.tournament.endpoints.ConverterException;
import org.tournament.endpoints.mappers.MatchMapper;
import org.tournament.endpoints.repositories.MatchRepository;

@Service
public class MatchService {
    private final MatchMapper mapper;
    private final MatchRepository repository;

    public MatchService(MatchMapper mapper, MatchRepository repository){
        this.mapper = mapper;
        this.repository = repository;
    }

    public void createMatch(MatchDTO match) throws ConverterException, JpaSystemException {
        MatchEntity entity = mapper.fromDTO(match);
        repository.save(entity);
    }
}
