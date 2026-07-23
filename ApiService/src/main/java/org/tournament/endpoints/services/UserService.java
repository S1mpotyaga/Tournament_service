package org.tournament.endpoints.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tournament.data.dto.UserDTO;
import org.tournament.data.entity.UserEntity;
import org.tournament.endpoints.ConverterException;
import org.tournament.endpoints.filters.pageable.PageableFilter;
import org.tournament.endpoints.filters.pageable.PageableUtils;
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

    @Transactional(readOnly = true)
    public UserDTO getUserById(int id)
            throws EntityNotFoundException, ConverterException
    {
        UserEntity userEntity = repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Not found user with id=" + id
                ));
        return mapper.fromEntity(userEntity);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers(PageableFilter filter)
            throws ConverterException
    {
        var pageable = PageableUtils.fromFilter(filter);
        return repository.findAll(pageable)
                .stream()
                .map(mapper::fromEntity)
                .toList();
    }
}
