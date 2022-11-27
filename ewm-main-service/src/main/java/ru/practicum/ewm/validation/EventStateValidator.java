package ru.practicum.ewm.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EventStateValidator {

    public List<EventState> validationStatesOrThrow(List<String> states) {
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
}
