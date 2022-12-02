package ru.practicum.ewm.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.repo.CompilationRepository;
import ru.practicum.ewm.exception.NotFoundException;

@Component
@RequiredArgsConstructor
public class CompilationValidator {
    private final CompilationRepository compilationRepository;

    public Compilation validateCompilationOrThrow(long id) {
        return compilationRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Compilation id = %x not found.", id), "Model not found."));
    }
}
