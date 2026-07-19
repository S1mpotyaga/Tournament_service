package org.tournament.endpoints.services;

import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.tournament.data.dto.UserDTO;
import org.tournament.data.entity.UserEntity;
import org.tournament.endpoints.ConverterException;
import org.tournament.endpoints.mappers.UserMapper;
import org.tournament.endpoints.repositories.UserRepository;

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
}
