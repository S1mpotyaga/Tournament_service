package tournament.service.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDomain(UserEntity user){
        return new UserDTO(
                user.getId(),
                user.getFullName(),
                user.getUserRole(),
                user.getPasswordHashCode(),
                user.getNick(),
                user.getRegistrationDate(),
                user.getEmail()
        );

    }

    public UserEntity toEntity(UserDTO user) {
        return new UserEntity(
                user.id(),
                user.fullName(),
                user.userRole(),
                user.passwordHashCode(),
                user.nick(),
                user.registrationDate(),
                user.email()
        );
    }
}
