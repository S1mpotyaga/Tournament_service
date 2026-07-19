package org.tournament.endpoints.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tournament.data.entity.TournamentEntity;

@Repository
public interface TournamentRepository extends JpaRepository<TournamentEntity, Integer> {
}
