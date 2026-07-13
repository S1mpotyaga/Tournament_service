package tournament.service.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserDTO getUserById(long id){
        UserEntity userEntity = repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Entity not found: id = " + id)
                );
        return mapper.toDomain(userEntity);
    }

    public UserDTO createUser(UserDTO userToCreate) {

        if(userToCreate.userRole() != null){
            throw new IllegalArgumentException("Status must be null");
        }

        if(userToCreate.id() != null){
            throw new IllegalArgumentException("Id must be null!");
        }

        var userEntity = mapper.toEntity(userToCreate);

        userEntity.setUserRole(UserRole.GUEST);
        userEntity.setRegistrationDate(LocalDateTime.now());

        var savedEntity = repository.save(userEntity);
        return mapper.toDomain(savedEntity);

    }
}
