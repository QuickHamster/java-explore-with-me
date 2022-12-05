package ru.practicum.ewm.comment.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.comment.model.Comment;

import java.util.List;

public interface CommentRepositoryCustom extends JpaRepository<Comment, Long> {

    Long countAllByEventId(Long eventId);

    @Query("select c " +
            "from Comment c " +
            "where c.commentator.id = :commentatorId " +
            "group by c.id")
    List<Comment> findAllByCommentatorId(Long commentatorId);

    @Query("select c " +
            "from Comment c " +
            "where c.event.id = :eventId " +
            "group by c.id")
    List<Comment> findAllByEventId(Long eventId);

    @Query("select c " +
            "from Comment c " +
            "where c.commentator.id = :commentatorId and c.id = :commentId")
    Comment findByCommentatorAndCommentIds(Long commentatorId, Long commentId);

    @Query("select c " +
            "from Comment c " +
            "where c.commentator.id = :commentatorId and c.event.id = :eventId " +
            "group by c.id")
    List<Comment> findAllByCommentatorAndEventIds(Long commentatorId, Long eventId);
}
