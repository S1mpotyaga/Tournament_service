package tournament.service.tournament;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(
        access = AccessLevel.PROTECTED
)

@Builder

@Entity
@Table(name = "tournament")
public class TournamentEntity {

    @Id
    @Column(name = "tournament_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tournamentId;

    @Column(name = "name",
            nullable = false,
            length = 100)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "bracket_type",
        nullable = false)
    @Enumerated(EnumType.STRING)
    private TournamentBracketType tournamentBracketType;

    @Column(name = "status",
            nullable = false)
    @Enumerated(EnumType.STRING)
    private TournamentStatus tournamentStatus;

    @Column(name = "max_participants")
    private Integer maxParticipants;

    @Column(name = "registration_start",
            nullable = false)
    private LocalDateTime registrationStart;

    @Column(name = "registration_end",
            nullable = false)
    private LocalDateTime registrationEnd;

    @Column(name = "tournament_start",
        nullable = false)
    private LocalDateTime tournamentStart;

    @Column(name = "tournament_end")
    private LocalDateTime tournamentEnd;

    @Column(name = "created_at",
            nullable = false)
    private LocalDateTime createdAt;

}
