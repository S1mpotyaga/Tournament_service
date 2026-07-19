package org.tournament.endpoints.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tournament.data.dto.TournamentParticipantDTO;
import org.tournament.data.entity.TournamentParticipantEntity;

@Repository
public interface TournamentParticipantRepository extends JpaRepository<TournamentParticipantEntity, Integer> {
}
