package tournament.service.user;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserDTO(

        @Null
        Long id,

        @NotBlank
        String fullName,

        UserRole userRole,

        @NotBlank
        String passwordHashCode,

        @NotBlank
        String nick,

        @PastOrPresent
        LocalDateTime registrationDate,

        @Email
        String email
) {
}
