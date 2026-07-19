package org.tournament.endpoints.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tournament.data.entity.MatchEntity;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<MatchEntity, Integer> {

    @Query("""
    SELECT m FROM MatchEntity m
    where(:tournamentId == null or m.tournament == :tournamentEntity)
""")
    List<MatchEntity> searchAllByFilter(
            Integer tournamentId,
            Pageable pageable
    );

    @Query("""
    SELECT m FROM MatchEntity m
    where(:tournamentId IS NULL OR m.tournament.tournamentId = :tournamentId)
    and(m.player1.userId = :userId or m.player2.userId = :userId)
""")
    List<MatchEntity> findByUserIdAndTournamentId(
            @Param("userId") int userId,
            @Param("tournamentId") Integer tournamentId,
            Pageable pageable
    );
}
