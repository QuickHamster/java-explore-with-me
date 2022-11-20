package ru.practicum.ewm.event.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.validation.EventDateInFuture;
import ru.practicum.validation.ExistingCategory;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.util.Constants.DEFAULT_DATE_TIME_FORMAT;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class NewEventDto {
    /*@NotNull
    @Size(min = 3, max = 256)*/
    private String title;
    /*@NotNull
    @Size(min = 20, max = 1024)*/
    private String annotation;
    /*@NotNull
    @Size(min = 20, max = 4096)*/
    private String description;
    /*@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DEFAULT_DATE_TIME_FORMAT)*/
    private LocalDateTime eventDate;
    private Long category;
    private Boolean paid = false;
    private Boolean requestModeration = true;
    /*@PositiveOrZero*/
    private Integer participantLimit = 0;
    /*@NotNull*/
    private Location location;
}
