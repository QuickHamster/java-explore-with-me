package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.model.dto.NewUserRequest;
import ru.practicum.ewm.user.model.dto.UserDto;
import ru.practicum.ewm.user.model.dto.UserMapper;
import ru.practicum.ewm.user.repo.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        log.info("Trying get users: {}.", ids);
        List<User> users;
        if (ids != null) {
            users = userRepository.findAllById(ids);
        } else {
            Pageable pageable = PageRequest.of(from / size, size);
            users = userRepository.findAll(pageable).getContent();
        }
        log.info("Users get successfully: {}.", users);
        return UserMapper.toUserDtoList(users);
    }

    @Override
    public UserDto getUserById(long id) {
        log.info("Trying get user: {}.", id);
        User user =  userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("User id = %x not found.", id), "Model not found."));
        log.info("User get successfully: {}.", user);
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto addUser(NewUserRequest userRequest) {
        log.info("Trying to add a user: {}.", userRequest);
        User user = UserMapper.fromNewUserRequestToUser(userRequest);
        user = userRepository.save(user);
        log.info("User added successfully: {}.", user);
        return UserMapper.toUserDto(user);
    }

    @Override
    public long deleteUser(long id) {
        log.info("Trying to delete a user: {}.", id);
        validationUser(List.of(id));
        userRepository.deleteById(id);
        log.info("User delete successfully: {}.", id);
        return id;
    }

    private void validationUser(List<Long> ids) {
        if (ids != null) {
            for (Long id : ids) {
                if (!userRepository.existsById(id)) {
                    throw new NotFoundException(String.format("User id = %x not found.", id), "Model not found.");
                }
            }
        }
    }
}
