package ru.practicum.ewm.comment.service;

import ru.practicum.ewm.comment.model.dto.CommentDto;
import ru.practicum.ewm.comment.model.dto.NewCommentDto;

import java.util.List;

public interface CommentService {

    CommentDto addComment(Long userId, Long eventId, NewCommentDto commentInputDto);

    CommentDto updateComment(Long userId, Long commentId, NewCommentDto commentInputDto);

    void deleteComment(Long userId, Long commentId);

    CommentDto getCommentByOwnerAndCommentIds(Long userId, Long commentId);

    List<CommentDto> getAllCommentsByOwnerId(Long userId);

    List<CommentDto> getAllCommentsByOwnerAndEventIds(Long userId, Long eventId);

    CommentDto moderateCommentByAdmin(Long commentId, NewCommentDto commentInputDto);

    void deleteCommentAdmin(Long commentId);

    void banUser(Long userId, String timeToUnban);

    void unbanUser(Long userId);

    CommentDto getCommentAdmin(Long commentId);

    List<CommentDto> findCommentsAdminByEventId(Long eventId);

    List<CommentDto> findCommentsAdminByCommentatorId(Long commentatorId);

    List<CommentDto> findCommentsAdminByCommentatorAndEventIds(Long userId, Long eventId);
}
