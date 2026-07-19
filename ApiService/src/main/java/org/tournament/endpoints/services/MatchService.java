package org.tournament.endpoints.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.tournament.data.dto.MatchDTO;
import org.tournament.data.entity.MatchEntity;
import org.tournament.endpoints.ConverterException;
import org.tournament.endpoints.filters.MatchSearchFilter;
import org.tournament.endpoints.mappers.MatchMapper;
import org.tournament.endpoints.repositories.MatchRepository;

import java.util.List;

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

    public MatchDTO getMatchById(int id) {
        MatchEntity matchEntity = repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Not found match with id=" + id
                ));
        return mapper.fromEntity(matchEntity);
    }

    public List<MatchDTO> getAllMatchByFilter(MatchSearchFilter filter) {
        int pageSize = filter.pageSize() != null ? filter.pageSize() : 15;
        int pageNumber = filter.pageNumber() != null ? filter.pageNumber() : 0;
        var pageable = Pageable.ofSize(pageSize).withPage(pageNumber);

        List<MatchEntity> matchEntities = repository.searchAllByFilter(
                filter.tournamentId(),
                pageable
        );
        return matchEntities.stream().map(mapper::fromEntity).toList();
    }
}
