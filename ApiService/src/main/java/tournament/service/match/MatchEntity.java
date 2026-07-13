package tournament.service.match;

import jakarta.persistence.*;
import lombok.*;
import tournament.service.tournament.TournamentEntity;
import tournament.service.tournament.participant.TournamentParticipantEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor(
        access = AccessLevel.PROTECTED
)

@Builder

@Entity
@Table(name = "matches")
public class MatchEntity {

    @Id
    @Column(name = "match_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "participant_id_1",
            nullable = false)
    private TournamentParticipantEntity participant1;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "participant_id_2",
            nullable = false)
    private TournamentParticipantEntity participant2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id")
    private TournamentParticipantEntity winner;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tournament_id",
            nullable = false)
    private TournamentEntity tournament;

    @Builder.Default
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MatchStatus status = MatchStatus.PENDING;

}
