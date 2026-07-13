package tournament.service.tournament.participant;

import jakarta.persistence.*;
import lombok.*;
import tournament.service.tournament.TournamentEntity;
import tournament.service.user.UserEntity;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(
        access = AccessLevel.PROTECTED
)

@Builder

@Entity
@Table(name = "tournament_participant")
public class TournamentParticipantEntity {

    @Id
    @Column(name = "tournament_participant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long participantId;

    @ManyToOne(fetch = FetchType.LAZY,
            optional = false)
    @JoinColumn(name = "user_id",
        nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY,
            optional = false)
    @JoinColumn(name = "tournament_id",
            nullable = false)
    private TournamentEntity tournament;

    @Builder.Default
    @Column(name = "participation_status",
            nullable = false)
    @Enumerated(EnumType.STRING)
    private TournamentParticipantStatus participantStatus = TournamentParticipantStatus.PENDING;

    @Builder.Default
    @Column(name = "registration_date",
        nullable = false)
    private LocalDateTime registrationDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY,
            optional = false)
    @JoinColumn(name = "created_by",
            nullable = false)
    private UserEntity createdBy;

}
