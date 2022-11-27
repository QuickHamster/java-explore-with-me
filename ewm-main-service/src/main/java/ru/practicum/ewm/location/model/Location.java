package ru.practicum.ewm.location.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Location {
    @Digits(integer=10, fraction=1)
    private float lon;
    @Digits(integer=10, fraction=1)
    private float lat;
}