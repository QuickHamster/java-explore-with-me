package ru.practicum.ewm.request.model.dto;

import ru.practicum.ewm.Const;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class RequestMapper {
    public static ParticipationRequestDto toDtoRequest(Request request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .created(LocalDateTime.parse(request.getCreated().format(Const.DATE_TIME_FORMATTER)))
                .event(request.getEvent().getId())
                .requester(request.getRequester().getId())
                .status(RequestStatus.valueOf(request.getStatus().toString()))
                .build();
    }

    public static List<ParticipationRequestDto> requestDtoList(List<Request> requests) {
        return requests.stream().map(RequestMapper::toDtoRequest).collect(Collectors.toList());
    }
}
