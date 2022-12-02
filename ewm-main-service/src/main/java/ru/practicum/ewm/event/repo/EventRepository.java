package ru.practicum.ewm.event.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventState;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>  {

    List<Event> findAllByInitiatorId(Long userId, Pageable pageable);

    @Query("select e " +
            "from Event e " +
            "where e.initiator.id in :users " +
            "and e.category.id in :categories " +
            "and e.state in :states " +
            "and e.eventDate between :startDate and :endDate " +
            "order by e.eventDate DESC")
    List<Event> findAllByUsersAndCategoriesAndStates(List<Long> users, List<Long> categories,
                                                  List<EventState> states, LocalDateTime startDate,
                                                  LocalDateTime endDate, Pageable pageable);

    @Query("select e " +
            "from Event e " +
            "where e.state = :state " +
            "and e.category.id in :categories " +
            "and e.annotation like concat('%', :text, '%') " +
            "and e.paid = :paid " +
            "and e.eventDate between :startDate and :endDate")
    List<Event> findAllEventsByParameters(
            EventState state, List<Long> categories, String text, Boolean paid,
            LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    @Query("select e " +
            "from Event e " +
            "where e.initiator.id = :userId")
    Event getByInitiator(Long userId);
}
