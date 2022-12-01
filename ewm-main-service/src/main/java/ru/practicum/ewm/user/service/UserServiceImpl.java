package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.AlreadyExistException;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.model.dto.NewUserRequest;
import ru.practicum.ewm.user.model.dto.UserDto;
import ru.practicum.ewm.user.model.dto.UserMapper;
import ru.practicum.ewm.user.repo.UserRepository;
import ru.practicum.ewm.validation.UserValidator;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserValidator validator;

    @Override
    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        log.info("Trying get users: {}.", ids);
        Pageable pageable = PageRequest.of(from / size, size);
        List<User> users = (ids != null)
                ? userRepository.findAllById(ids)
                : userRepository.findAll(pageable).getContent();
        log.info("Users get successfully: {}.", users);
        return UserMapper.toUserDtoList(users);
    }

    @Override
    public UserDto addUser(NewUserRequest userRequest) {
        log.info("Trying to add a user: {}.", userRequest);
        User user = UserMapper.fromNewUserRequestToUser(userRequest);
        try {
            user = userRepository.save(user);
        } catch (Exception ex) {
            throw new AlreadyExistException("Can't create this user.", String.format("User %s already exist.",
                    user.getName()));
        }
        log.info("User added successfully: {}.", user);
        return UserMapper.toUserDto(user);
    }

    @Override
    public void deleteUser(long id) {
        log.info("Trying to delete a user: {}.", id);
        validator.validateUsers(List.of(id));
        userRepository.deleteById(id);
        log.info("User delete successfully: {}.", id);
    }
}
