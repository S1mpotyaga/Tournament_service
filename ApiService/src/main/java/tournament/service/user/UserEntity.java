package tournament.service.user;

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

    @Builder.Default
    @Column(name = "role",
            nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.GUEST;

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
}
