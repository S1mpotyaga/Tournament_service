package org.tournament.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.tournament.data.enums.TournamentStatus;
import org.tournament.data.enums.TournamentBracketType;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tournament")
@NoArgsConstructor
@AllArgsConstructor
public class TournamentEntity {
    @Id
    @Column(name = "tournament_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tournamentId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(name = "bracket_type", nullable = false)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    private TournamentBracketType tournamentBracketType;
    @Column(name = "status", nullable = false)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    private TournamentStatus tournamentStatus;
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
}
