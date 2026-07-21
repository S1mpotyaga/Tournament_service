package org.tournament.endpoints.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tournament.data.entity.UserEntity;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("""
         SELECT u FROM UserEntity u
         WHERE(:userId is null or u.userId = :userId)
""")
    List<UserEntity> searchAllByFilter(
            @Param("userId") Integer userId,
            Pageable pageable
    );
}
