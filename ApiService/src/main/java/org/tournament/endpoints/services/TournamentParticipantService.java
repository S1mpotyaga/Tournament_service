package org.tournament.endpoints.services;

import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.tournament.data.dto.TournamentParticipantDTO;
import org.tournament.data.entity.TournamentParticipantEntity;
import org.tournament.endpoints.ConverterException;
import org.tournament.endpoints.mappers.TournamentParticipantMapper;
import org.tournament.endpoints.repositories.TournamentParticipantRepository;

@Service
public class TournamentParticipantService {
    private final TournamentParticipantMapper mapper;
    private final TournamentParticipantRepository repository;

    public TournamentParticipantService(
            TournamentParticipantMapper mapper,
            TournamentParticipantRepository repository
    ){
        this.mapper = mapper;
        this.repository = repository;
    }

    public void createParticipant(TournamentParticipantDTO participant) throws ConverterException, JpaSystemException {
        TournamentParticipantEntity entity = mapper.fromDTO(participant);
        repository.save(entity);
    }

}
