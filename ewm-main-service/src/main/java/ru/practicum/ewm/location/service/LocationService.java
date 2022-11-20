package ru.practicum.ewm.location.service;

import ru.practicum.ewm.location.model.dto.LocationDto;
import ru.practicum.ewm.location.model.dto.LocationInputDto;

public interface LocationService {

    LocationDto addLocation(LocationInputDto locationInputDto);

    LocationDto updateLocation(LocationDto locationDto);

    long deleteLocation(long id);
}