package ru.practicum.ewm.user.model.dto;

import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.util.Const;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .isBan(user.isCommentsBan().toString().concat(" (")
                        .concat(user.getBanCommentsPeriod().format(Const.DATE_TIME_FORMATTER)).concat(")"))
                .build();
    }

    public static List<UserDto> toUserDtoList(List<User> users) {
        return users.stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    public static UserShortDto toUserShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    public static User fromNewUserRequestToUser(NewUserRequest userRequest) {
        return User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .banCommentsPeriod(LocalDateTime
                        .ofEpochSecond(0L, 0, ZoneOffset.UTC))
                .build();
    }
}
