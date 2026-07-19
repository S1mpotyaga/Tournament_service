package org.tournament.endpoints.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
            Integer tournamentId, Pageable pageable
    );
}
