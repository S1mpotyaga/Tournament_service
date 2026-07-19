package org.tournament.endpoints.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.tournament.data.dto.UserDTO;
import org.tournament.data.entity.UserEntity;
import org.tournament.endpoints.ConverterException;
import org.tournament.endpoints.filters.UserSearchFilter;
import org.tournament.endpoints.mappers.UserMapper;
import org.tournament.endpoints.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public UserService(UserRepository repository, UserMapper userMapper){
        this.repository = repository;
        this.mapper = userMapper;
    }

    public void createUser(UserDTO user) throws JpaSystemException, ConverterException {
        UserEntity userEntity = mapper.fromDTO(user);
        repository.save(userEntity);
    }

    public UserDTO getUserById(int id){
        UserEntity userEntity = repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Пользователя с данным id не найдено"
                ));
        return mapper.fromEntity(userEntity);
    }

    public List<UserDTO> searchAllByFilter(UserSearchFilter filter){
        int pageSize = filter.pageSize() != null ? filter.pageSize() : 15;
        int pageNumber = filter.pageNumber() != null ? filter.pageNumber() : 0;
        var pageable = Pageable.ofSize(pageSize).withPage(pageNumber);

        List<UserEntity> allUserEntities = repository.searchAllByFilter(
                filter.userId(),
                filter.nick(),
                pageable
        );
        return allUserEntities.stream().map(mapper::fromEntity).toList();
    }
}
