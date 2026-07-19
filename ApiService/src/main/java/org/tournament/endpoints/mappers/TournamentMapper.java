package org.tournament.endpoints.mappers;

import org.springframework.stereotype.Component;
import org.tournament.data.dto.TournamentDTO;
import org.tournament.data.entity.TournamentEntity;
import org.tournament.endpoints.ConverterException;

import java.time.LocalDateTime;

@Component
public class TournamentMapper {
    public TournamentEntity fromDTO(TournamentDTO tournamentDTO) throws ConverterException {
        try{
            TournamentEntity entity = new TournamentEntity();
            entity.setName(tournamentDTO.getName());
            if (tournamentDTO.getDescription() == null){
                entity.setDescription("");
            } else{
                entity.setDescription(tournamentDTO.getDescription());
            }
            entity.setTournamentStatus(tournamentDTO.getTournamentStatus());
            entity.setTournamentBracketType(tournamentDTO.getTournamentBracketType());
            if (tournamentDTO.getCreatedDate() == null){
                entity.setCreatedDate(LocalDateTime.now());
            }else {
                entity.setCreatedDate(tournamentDTO.getCreatedDate());
            }
            return entity;
        } catch (Exception e) {
            throw new ConverterException("Impossible transformation from dto: " + tournamentDTO.toString() + " - to entity.");
        }
    }

    public TournamentDTO fromEntity(TournamentEntity tournamentEntity) throws ConverterException{
        try{
            TournamentDTO dto = new TournamentDTO();
            dto.setTournamentId(tournamentEntity.getTournamentId());
            dto.setName(tournamentEntity.getName());
            dto.setDescription(tournamentEntity.getDescription());
            dto.setTournamentStatus(tournamentEntity.getTournamentStatus());
            dto.setCreatedDate(tournamentEntity.getCreatedDate());
            dto.setTournamentBracketType(tournamentEntity.getTournamentBracketType());
            return dto;
        } catch (Exception e) {
            throw new ConverterException("Impossible transformation from entity: " + tournamentEntity.toString() + " - to dto.");
        }
    }
}
