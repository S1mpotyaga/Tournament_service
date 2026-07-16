package org.tournament.data.dto;

import lombok.*;
import org.tournament.data.enums.UserRole;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String fullName;
    private UserRole role;
    private String password;
    private String nickname;
    private LocalDateTime registrationDime;
    private String email;
}
