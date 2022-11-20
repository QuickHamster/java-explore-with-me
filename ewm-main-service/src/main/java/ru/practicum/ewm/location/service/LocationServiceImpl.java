package ru.practicum.ewm.location.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.location.model.Location;
import ru.practicum.ewm.location.model.dto.LocationDto;
import ru.practicum.ewm.location.model.dto.LocationInputDto;
import ru.practicum.ewm.location.model.dto.LocationMapper;
import ru.practicum.ewm.location.repo.LocationRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    @Override
    @Transactional
    public LocationDto addLocation(LocationInputDto locationInputDto) {
        log.info("Trying to add location: {}.", locationInputDto);
        LocationDto locationDto = LocationMapper.toLocationDto(locationRepository
                .save(LocationMapper.toLocation(locationInputDto)));
        log.info("Location added successfully: {}.", locationDto);
        return locationDto;
    }

    @Override
    @Transactional
    public LocationDto updateLocation(LocationDto locationDto) {
        log.info("Trying to update a location: {}.", locationDto);
        Location location = validationLocation(locationDto.getId());
        if (locationDto.getLon() != null) {
            location.setLon(locationDto.getLon());
        }
        if (locationDto.getLat() != null) {
            location.setLat(locationDto.getLat());
        }
        location = locationRepository.save(location);
        log.info("Location update successfully: {}.", location);
        return LocationMapper.toLocationDto(location);
    }

    @Override
    @Transactional
    public long deleteLocation(long id) {
        log.info("Trying to delete a location: {}.", id);
        validationLocation(id);
        locationRepository.deleteById(id);
        log.info("Location delete successfully: {}.", id);
        return id;
    }

    private Location validationLocation(long id) {
        return locationRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Location id = %x not found.", id), "Model not found."));
    }
}