package org.tournament.endpoints.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tournament.data.entity.MatchEntity;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<MatchEntity, Integer> {

    @EntityGraph(attributePaths = {"player1", "player2", "tournament"})
    @Query("SELECT m FROM MatchEntity m")
    List<MatchEntity> findAllWithRelations(
            Pageable pageable
    );

    @EntityGraph(attributePaths = {"player1", "player2", "tournament"})
    @Query("""
    SELECT m FROM MatchEntity m
    where (m.player1.userId = :userId or m.player2.userId = :userId)
""")
    List<MatchEntity> findByUser(
            @Param("userId") int userId,
            Pageable pageable
    );
}
