package org.tournament.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.tournament.data.enums.UserRole;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "role", nullable = false)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.GUEST;
    @Column(nullable = false)
    private String password;
    @Column(unique = true)
    private String nick;
    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;
    @Column(nullable = false, unique = true)
    private String email;
}
