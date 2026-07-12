package tournament.service.match;

import jakarta.persistence.*;
import lombok.*;
import tournament.service.tournament.TournamentEntity;
import tournament.service.tournament.participant.TournamentParticipantEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Builder

@Entity
@Table(name = "match")
public class MatchEntity {

    @Id
    @Column(name = "match_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;

    @ManyToOne
    @JoinColumn(name = "participant_id_1")
    private TournamentParticipantEntity participant1;

    @ManyToOne
    @JoinColumn(name = "participant_id_2")
    private TournamentParticipantEntity participant2;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private TournamentParticipantEntity winner;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private TournamentEntity tournament;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;
}
