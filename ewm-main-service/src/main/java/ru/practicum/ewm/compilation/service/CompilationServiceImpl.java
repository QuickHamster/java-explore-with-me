package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.model.dto.CompilationDto;
import ru.practicum.ewm.compilation.model.dto.CompilationMapper;
import ru.practicum.ewm.compilation.model.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.repo.CompilationRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.validation.CompilationValidator;
import ru.practicum.ewm.validation.EventValidator;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final StatsClient statsClient;
    private final EventValidator eventValidator;
    private final CompilationValidator compilationValidator;

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Pageable page) {
        log.info("Trying to get compilations: pinned {}, page {}.", pinned, page);
        List<Compilation> compilationList =  compilationRepository.findAllByPinned(pinned, page);
        List<CompilationDto> compilationDtoList = CompilationMapper.toCompilationDtoList(compilationList, statsClient);
        log.info("Get compilations successfully: {}.", compilationDtoList);
        return compilationDtoList;
    }

    @Override
    public CompilationDto addCompilation(NewCompilationDto compilationDto) {
        log.info("Trying add compilation: compilationDto {}.", compilationDto);
        Set<Event> events = eventValidator.validationEventsOrThrow(compilationDto.getEvents());
        Compilation compilation = CompilationMapper.fromNewCompilationDtoToCompilation(compilationDto, events);
        compilation = compilationRepository.save(compilation);
        CompilationDto compilationOutDto = CompilationMapper.fromCompilationToCompilationDto(compilation, statsClient);
        log.info("Add compilation successfully: {}.", compilationOutDto);
        return compilationOutDto;
    }

    @Override
    public void deleteCompilation(Long compId) {
        log.info("Trying to delete compilation: compId {}.", compId);
        compilationRepository.delete(compilationValidator.validationCompilationOrThrow(compId));
        log.info("Delete compilation successfully: {}.", compId);
    }

    @Override
    public CompilationDto findCompilationById(Long compId) {
        log.info("Trying to find compilation: compId {}.", compId);
        Compilation compilation = compilationValidator.validationCompilationOrThrow(compId);
        CompilationDto compilationDto = CompilationMapper.fromCompilationToCompilationDto(compilation, statsClient);
        log.info("Find compilation successfully: {}.", compilationDto);
        return compilationDto;
    }

    @Override
    public void pinCompilation(Long compId) {
        log.info("Trying to pin compilation: compId {}.", compId);
        Compilation compilation = compilationValidator.validationCompilationOrThrow(compId);
        compilation.setPinned(true);
        compilation = compilationRepository.save(compilation);
        log.info("Compilation pin successfully: {}.", compilation);
    }

    @Override
    public void unpinCompilation(Long compId) {
        log.info("Trying to unpin compilation: compId {}.", compId);
        Compilation compilation = compilationValidator.validationCompilationOrThrow(compId);
        compilation.setPinned(false);
        compilation = compilationRepository.save(compilation);
        log.info("Compilation unpin successfully: {}.", compilation);

    }

    @Override
    public void addEventToCompilation(Long compId, Long eventId) {
        log.info("Trying to add event to compilation: compId {}, eventId {}.", compId, eventId);
        Compilation compilation = compilationValidator.validationCompilationOrThrow(compId);
        Event event = eventValidator.validationEventOrThrow(eventId);
        compilation.getEventSet().add(event);
        compilation = compilationRepository.save(compilation);
        log.info("Add event to compilation successfully: {}", compilation);
    }

    @Override
    public void deleteEventFromCompilation(Long compId, Long eventId) {
        log.info("Trying to delete event from compilation: compId {}, eventId {}.", compId, eventId);
        Compilation compilation = compilationValidator.validationCompilationOrThrow(compId);
        compilation.getEventSet().removeIf(event -> event.getId().equals(eventId));
        compilation = compilationRepository.save(compilation);
        log.info("Delete event from compilation successfully: {}", compilation);
    }
}
