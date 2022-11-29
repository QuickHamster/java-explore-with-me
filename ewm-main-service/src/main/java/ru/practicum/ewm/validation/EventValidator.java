package ru.practicum.ewm.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repo.EventRepository;
import ru.practicum.ewm.exception.ForbiddenException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ValidationException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class EventValidator {
    private final EventRepository eventRepository;

    public Event validationEventOrThrow(long id) {
        return eventRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Event id = %x not found.", id), "Model not found."));
    }

    public Set<Event> validationEventsOrThrow(List<Long> ids) {
        Set<Event> events = new HashSet<>();
        for (Long id : ids) {
            try {
                Event event = validationEventOrThrow(id);
                events.add(event);
            } catch (IllegalArgumentException ex) {
                throw new NotFoundException("Impossible to add an event " + id + " to the set.", "Internal error.");
            }
        }
        return events;
    }

    public void validateThatUserIdIsInitiatorEventOrThrow(Event event, Long userId) {
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException("User" + userId + "is not the initiator of the event " + event.getId() + ".",
                    "Forbidden operation.");
        }
    }

    public void validateParticipantLimitNotReachedOrThrow(Event event) {
        if (event.getParticipantLimit().equals(event.getConfirmedRequests())) {
            throw new ValidationException("Membership limit reached.","Validation error.");
        }
    }

    public void validateInitiatorCantSendRequestHimselfOrThrow(Event event, Long userId) {
        if (event.getInitiator().getId().equals(userId)) {
            throw new ValidationException("Initiator can't send request himself.","Validation error.");
        }
    }
}
