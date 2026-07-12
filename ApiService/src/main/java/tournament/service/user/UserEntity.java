package tournament.service.user;

import jakarta.persistence.*;
import lombok.*;
import tournament.service.tournament.participant.TournamentParticipantEntity;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Builder

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name",
            nullable = false,
            length = 100)
    private String fullName;

    @Column(name = "role",
            nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(name = "password_hash_code",
            nullable = false,
            length = 255)
    private String passwordHashCode;

    @Column(name = "nick",
            nullable = false,
            unique = true,
            length = 50)
    private String nick;

    @Column(name = "registration_date",
            nullable = false)
    private LocalDateTime registrationDate;

    @Column(name = "email",
        nullable = false,
        unique = true,
        length = 100)
    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<TournamentParticipantEntity> participations;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<TournamentParticipantEntity> createdParticipations;
}
