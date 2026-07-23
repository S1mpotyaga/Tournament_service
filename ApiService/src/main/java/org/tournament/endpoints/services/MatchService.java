package org.tournament.endpoints.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tournament.data.dto.MatchDTO;
import org.tournament.data.entity.MatchEntity;
import org.tournament.endpoints.ConverterException;
import org.tournament.endpoints.filters.pageable.IdPaginationFilter;
import org.tournament.endpoints.filters.pageable.PageableFilter;
import org.tournament.endpoints.filters.pageable.PageableUtils;
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

    @Transactional(readOnly = true)
    public MatchDTO getMatchById(int id)
        throws EntityNotFoundException, ConverterException
    {
        MatchEntity matchEntity = repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Not found match with id=" + id
                ));
        return mapper.fromEntity(matchEntity);
    }

    @Transactional(readOnly = true)
    public List<MatchDTO> findAllMatches(PageableFilter filter)
        throws ConverterException
    {
        var pageable = PageableUtils.fromFilter(filter);
        return repository.findAllWithRelations(pageable)
                .stream()
                .map(mapper::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MatchDTO> findMatchesByUser(IdPaginationFilter filter)
        throws ConverterException
    {
        var pageable = PageableUtils.fromFilter(filter);
        return repository.findByUser(filter.Id(), pageable)
                .stream()
                .map(mapper::fromEntity)
                .toList();
    }
}
