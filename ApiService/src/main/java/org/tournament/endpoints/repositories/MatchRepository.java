package org.tournament.endpoints.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tournament.data.entity.MatchEntity;

@Repository
public interface MatchRepository extends JpaRepository<MatchEntity, Integer> {
}
