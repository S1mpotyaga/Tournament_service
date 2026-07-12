package tournament.service.tournament.participant;

import jakarta.persistence.*;
import lombok.*;
import tournament.service.tournament.TournamentEntity;
import tournament.service.user.UserEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Builder

@Entity
@Table(name = "tournament_participant")
public class TournamentParticipantEntity {

    @Id
    @Column(name = "tournament_participant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long participantId;

    @ManyToOne
    @JoinColumn(name = "user_id",
        nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private TournamentEntity tournament;

    @Column(name = "participation_status")
    @Enumerated(EnumType.STRING)
    private TournamentParticipantStatus participantStatus;

    @Column(name = "registration_date",
        nullable = false)
    LocalDateTime registrationDate;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private UserEntity createdBy;

}
