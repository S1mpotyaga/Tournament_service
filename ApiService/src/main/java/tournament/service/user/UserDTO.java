package tournament.service.user;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserDTO(
        @NotNull
        Long id,

        @NotNull
        String fullName,

        UserRole userRole,

        @NotNull
        String passwordHashCode,

        @NotNull
        String nick,

        @NotNull
        @PastOrPresent
        LocalDate registrationDate,

        @NotNull
        String email
) {
}
