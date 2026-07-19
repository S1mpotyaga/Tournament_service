package org.tournament.endpoints.mappers;

import org.springframework.stereotype.Component;
import org.tournament.data.dto.TournamentParticipantDTO;
import org.tournament.data.entity.TournamentParticipantEntity;
import org.tournament.endpoints.ConverterException;

@Component
public class TournamentParticipantMapper {
    private final TournamentMapper tournamentMapper;
    private final UserMapper userMapper;

    public TournamentParticipantMapper(TournamentMapper tournamentMapper, UserMapper userMapper){
        this.tournamentMapper = tournamentMapper;
        this.userMapper = userMapper;
    }

    public TournamentParticipantEntity fromDTO(TournamentParticipantDTO dto) throws ConverterException {
        try{
            TournamentParticipantEntity entity = new TournamentParticipantEntity();
            entity.setUser(userMapper.fromDTO(dto.getUser()));
            entity.setTournament(tournamentMapper.fromDTO(dto.getTournament()));
            entity.setRegistrationDate(dto.getRegistrationDate());
            return entity;
        } catch (Exception e) {
            throw new ConverterException("Impossible transformation from dto: " + dto.toString() + " - to entity.");
        }
    }

    public TournamentParticipantDTO fromEntity(TournamentParticipantEntity entity) throws ConverterException{
        try{
            TournamentParticipantDTO dto = new TournamentParticipantDTO();
            dto.setUser(userMapper.fromEntity(entity.getUser()));
            dto.setTournament(tournamentMapper.fromEntity(entity.getTournament()));
            dto.setRegistrationDate(entity.getRegistrationDate());
            return dto;
        } catch (Exception e) {
            throw new ConverterException("Impossible transformation from entity: " + entity.toString() + " - to dto.");
        }
    }
}
