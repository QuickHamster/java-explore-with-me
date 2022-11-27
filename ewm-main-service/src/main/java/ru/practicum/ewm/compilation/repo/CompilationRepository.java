package ru.practicum.ewm.compilation.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.compilation.model.Compilation;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    @Query("select c from Compilation c where c.pinned = :pinned")
    List<Compilation> findAllByPinned(Boolean pinned, Pageable pageable);
}

