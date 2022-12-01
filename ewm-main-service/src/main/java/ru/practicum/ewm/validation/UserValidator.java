package ru.practicum.ewm.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repo.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public User validateUserOrThrow(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("User id = %x not found.", id), "Model not found."));
    }

    public List<Long> validateUsers(List<Long> ids) {

        List<Long> results = new ArrayList<>();

        for (Long id : ids) {
            results.add(validateUserOrThrow(id).getId());
        }

        return results;
    }
}
