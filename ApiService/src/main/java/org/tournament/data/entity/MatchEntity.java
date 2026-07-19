package org.tournament.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.tournament.data.enums.MatchStatus;

@Getter
@Setter
@Entity
@Table(name = "tournament_match")
@AllArgsConstructor
@NoArgsConstructor
public class MatchEntity {
    @Id
    @Column(name = "match_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer matchId;
    @ManyToOne
    @JoinColumn(name = "participant_id_1")
    private UserEntity player1;
    @ManyToOne
    @JoinColumn(name = "participant_id_2")
    private UserEntity player2;
    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    private TournamentEntity tournament;
    @ManyToOne
    @JoinColumn(name = "winner_id")
    private UserEntity winner;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private MatchStatus matchStatus;
}
