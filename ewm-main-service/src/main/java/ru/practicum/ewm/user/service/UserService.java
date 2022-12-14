package ru.practicum.ewm.user.service;

import ru.practicum.ewm.user.model.dto.NewUserRequest;
import ru.practicum.ewm.user.model.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers(List<Long> ids, int from, int size);

    UserDto addUser(NewUserRequest userRequest);

    void deleteUser(long id);
}
