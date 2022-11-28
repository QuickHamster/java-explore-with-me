package ru.practicum.ewm.event.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.util.Const;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Getter
@Setter
public class UpdateEventRequest {
    @Size(min = 20, max = 2000)
    private String annotation;
    private Long category;
    @Size(min = 20, max = 7000)
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Const.DATE_TIME_FORMAT)
    private LocalDateTime eventDate;
    @NotNull
    private Long eventId;
    private Boolean paid;
    @PositiveOrZero
    private Integer participantLimit;
    @Size(min = 3, max = 120)
    private String title;
}
