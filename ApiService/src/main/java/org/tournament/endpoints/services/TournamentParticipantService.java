package org.tournament.endpoints.services;

import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tournament.data.dto.TournamentParticipantDTO;
import org.tournament.data.dto.UserDTO;
import org.tournament.data.entity.TournamentParticipantEntity;
import org.tournament.endpoints.ConverterException;
import org.tournament.endpoints.filters.pageable.IdPaginationFilter;
import org.tournament.endpoints.filters.pageable.PageableUtils;
import org.tournament.endpoints.mappers.TournamentParticipantMapper;
import org.tournament.endpoints.mappers.UserMapper;
import org.tournament.endpoints.repositories.TournamentParticipantRepository;

import java.util.List;

@Service
public class TournamentParticipantService {
    private final TournamentParticipantMapper tpMapper;
    private final TournamentParticipantRepository repository;
    private final UserMapper userMapper;

    public TournamentParticipantService(
            TournamentParticipantMapper tpMapper,
            TournamentParticipantRepository repository,
            UserMapper userMapper
    ){
        this.tpMapper = tpMapper;
        this.repository = repository;
        this.userMapper = userMapper;
    }

    public void createParticipant(TournamentParticipantDTO participant) throws ConverterException, JpaSystemException {
        TournamentParticipantEntity entity = tpMapper.fromDTO(participant);
        repository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsersByTournamentIdByFilter(IdPaginationFilter filter)
        throws ConverterException{
        var pageable = PageableUtils.fromFilter(filter);
        return repository.findByTournament_TournamentId(filter.Id(), pageable)
                .stream()
                .map(participant -> userMapper.fromEntity(participant.getUser()))
                .toList();
    }
}
