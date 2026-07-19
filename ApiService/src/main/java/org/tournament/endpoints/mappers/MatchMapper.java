package org.tournament.endpoints.mappers;

import org.springframework.stereotype.Component;
import org.tournament.data.dto.MatchDTO;
import org.tournament.data.entity.MatchEntity;
import org.tournament.endpoints.ConverterException;

@Component
public class MatchMapper {
    private final UserMapper userMapper;
    private final TournamentMapper tournamentMapper;

    public MatchMapper(UserMapper userMapper, TournamentMapper tournamentMapper){
        this.userMapper = userMapper;
        this.tournamentMapper = tournamentMapper;
    }

    public MatchEntity fromDTO(MatchDTO match) throws ConverterException{
        try {
            MatchEntity entity = new MatchEntity();
            entity.setMatchStatus(match.getMatchStatus());
            entity.setWinner(userMapper.fromDTO(match.getWinner()));
            entity.setTournament(tournamentMapper.fromDTO(match.getTournament()));
            entity.setPlayer1(userMapper.fromDTO(match.getPlayer1()));
            entity.setPlayer2(userMapper.fromDTO(match.getPlayer2()));
            return entity;
        } catch (Exception e) {
            throw new ConverterException("Impossible transformation from dto: " + match.toString() + " - to entity.");
        }
    }

    public MatchDTO fromEntity(MatchEntity match) throws ConverterException{
        try {
            MatchDTO dto = new MatchDTO();
            dto.setMatchStatus(match.getMatchStatus());
            dto.setWinner(userMapper.fromEntity(match.getWinner()));
            dto.setTournament(tournamentMapper.fromEntity(match.getTournament()));
            dto.setPlayer1(userMapper.fromEntity(match.getPlayer1()));
            dto.setPlayer2(userMapper.fromEntity(match.getPlayer2()));
            return dto;
        } catch (Exception e) {
            throw new ConverterException("Impossible transformation from entity: " + match.toString() + " - to dto.");
        }
    }
}
