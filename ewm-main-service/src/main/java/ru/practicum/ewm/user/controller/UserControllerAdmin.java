package ru.practicum.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.model.dto.NewUserRequest;
import ru.practicum.ewm.user.model.dto.UserDto;
import ru.practicum.ewm.user.service.UserService;
import ru.practicum.ewm.util.Const;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class UserControllerAdmin {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers(
            @RequestParam(required = false,
                    defaultValue = "List.of()") @Positive List<Long> ids,
            @RequestParam(value = "from",
                    required = false,
                    defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = Const.SIZE_OF_PAGE) @Positive Integer size) {
        log.info("Get all users: ids {}, from {}, size {}.", ids, from, size);
        return userService.getUsers(ids, from, size);
    }

    @PostMapping
    public UserDto addUser(@Valid @RequestBody NewUserRequest newUserRequest) {
        log.info("Add user: {}.", newUserRequest);
        return userService.addUser(newUserRequest);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable @Min(0) Long userId) {
        log.info("Delete user: id {}", userId);
        userService.deleteUser(userId);
    }
}
