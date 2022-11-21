package ru.practicum.ewm.event.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewm.location.model.Location;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class NewEventDto {
    /*@NotNull
    @Size(min = 20, max = 1024)*/
    private String annotation;

    private Long category;

    /*@NotNull
    @Size(min = 20, max = 4096)*/
    private String description;

    /*@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DEFAULT_DATE_TIME_FORMAT)*/
    private LocalDateTime eventDate;

    /*@NotNull*/
    private Location location;

    private Boolean paid = false;

    /*@PositiveOrZero*/
    private Integer participantLimit = 0;

    private Boolean requestModeration = true;

    /*@NotNull
    @Size(min = 3, max = 256)*/
    private String title;
}
