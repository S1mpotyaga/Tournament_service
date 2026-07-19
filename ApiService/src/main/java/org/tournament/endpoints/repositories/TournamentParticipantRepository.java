package org.tournament.endpoints.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tournament.data.entity.TournamentParticipantEntity;
import java.util.List;

@Repository
public interface TournamentParticipantRepository extends JpaRepository<TournamentParticipantEntity, Integer> {

    List<TournamentParticipantEntity> findByTournament_TournamentId(
            Integer tournamentId,
            Pageable pageable
    );
}
