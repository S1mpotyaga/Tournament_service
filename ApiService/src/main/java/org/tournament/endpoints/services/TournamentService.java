package org.tournament.endpoints.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.tournament.data.dto.TournamentDTO;
import org.tournament.data.entity.TournamentEntity;
import org.tournament.endpoints.ConverterException;
import org.tournament.endpoints.mappers.TournamentMapper;
import org.tournament.endpoints.repositories.TournamentRepository;

@Service
public class TournamentService {
    private final TournamentMapper mapper;
    private final TournamentRepository repository;

    public TournamentService(TournamentMapper mapper, TournamentRepository repository){
        this.mapper = mapper;
        this.repository = repository;
    }

    public void createTournament(TournamentDTO tournamentDTO) throws ConverterException, JpaSystemException {
        TournamentEntity entity = mapper.fromDTO(tournamentDTO);
        repository.save(entity);
    }

    public TournamentDTO getTournamentById(int id) {
        TournamentEntity tournamentEntity = repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Not found tournament with id: " + id
                ));
        return mapper.fromEntity(tournamentEntity);
    }
}
