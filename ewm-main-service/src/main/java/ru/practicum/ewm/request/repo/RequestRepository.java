package ru.practicum.ewm.request.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestStatus;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long>  {

   @Query("select r " +
           "from Request r " +
           "where r.requester.id = :id")
    List<Request> getAllByRequesterId(long id);
    @Query("select r " +
            "from Request r " +
            "where r.event.initiator.id = :userId and r.event.id = :eventId")
    List<Request> findOwnerEventRequests(Long userId, Long eventId);

    @Query("select r " +
            "from Request r " +
            "where r.event = :event and r.status <> :status")
    List<Request> getAllByEventAndStatus(Event event, RequestStatus status);

    boolean existsByRequesterIdAndEventIdAndStatus(Long userId, Long eventId, RequestStatus requestState);
}
