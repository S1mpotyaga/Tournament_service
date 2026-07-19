package org.tournament.data.dto;

import lombok.*;
import org.tournament.data.enums.UserRole;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer userId;
    private String fullName;
    private UserRole role;
    private String password;
    private String nick;
    private LocalDateTime registrationTime;
    private String email;
}
