package ru.practicum.ewm.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.exception.ForbiddenException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EventStateValidator {

    public List<EventState> validateStatesOrThrow(List<String> states) {
        List<EventState> stateList = new ArrayList<>();
        for (String state : states) {
            try {
                stateList.add(EventState.valueOf(state));
            } catch (IllegalArgumentException err) {
                throw new NotFoundException("Event state: " + state + " not found.", "Model not found.");
            }
        }
        return stateList;
    }

    public void validateEventStatePublishedOrThrow(Event event) {
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ForbiddenException("Unable to change event " + event.getId() + " in state PUBLISHED.",
                    "Forbidden operation.");
        }
    }

    public void validateEventStateNotPendingOrThrow(Event event) {
        if (!event.getState().equals(EventState.PENDING)) {
            throw new ForbiddenException("Unable to reject event " + event.getId() + " not in state PENDING.",
                    "Forbidden operation.");
        }
    }

    public void validateEventStateNotPublishedOrThrow(Event event) {
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ForbiddenException("Event " + event.getId() + " not in state PUBLISHED.",
                    "Forbidden operation.");
        }
    }

    public void validateThatEventStateNotPublishedOrThrow(Event event) {
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ValidationException("Event don't PUBLISHED.","Validation error.");
        }
    }
}
