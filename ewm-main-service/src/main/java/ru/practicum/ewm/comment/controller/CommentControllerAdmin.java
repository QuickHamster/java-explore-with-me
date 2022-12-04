package ru.practicum.ewm.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.model.dto.CommentDto;
import ru.practicum.ewm.comment.model.dto.NewCommentDto;
import ru.practicum.ewm.comment.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/comments")
public class CommentControllerAdmin {

    private final CommentService commentService;

    @GetMapping("/users/{userId}")
    public List<CommentDto> findCommentsAdminByCommentatorId(@PathVariable(value = "userId") @Positive Long userId) {
        log.info("Find all comments by admin: userId {}.", userId);
        return commentService.findCommentsAdminByCommentatorId(userId);
    }

    @GetMapping("/{commentId}")
    public CommentDto getCommentAdmin(@PathVariable(value = "commentId") @Positive Long commentId) {
        log.info("Get comment by admin: commentId {}.", commentId);
        return commentService.getCommentAdmin(commentId);
    }

    @GetMapping("/events/{eventId}")
    public List<CommentDto> findCommentsAdminByEventId(@PathVariable(value = "eventId") @Positive Long eventId) {
        log.info("Find all comments by admin: eventId {}.", eventId);
        return commentService.findCommentsAdminByEventId(eventId);
    }

    @GetMapping("/events/{eventId}/users/{userId}")
    public List<CommentDto> findCommentsAdminByCommentatorAndEventIds(
            @PathVariable(value = "userId") @Positive Long userId,
            @PathVariable(value = "eventId") @Positive Long eventId) {
        log.info("Find all comments by admin: userId {}, eventId {}.", userId, eventId);
        return commentService.findCommentsAdminByCommentatorAndEventIds(userId, eventId);
    }

    @PatchMapping("/{commentId}")
    public CommentDto moderateCommentByAdmin(@PathVariable(value = "commentId") @Positive Long commentId,
                                       @RequestBody @Valid NewCommentDto newCommentDto) {
        log.info("Moderate a comment by an admin: commentId {}, newCommentDto {}.", commentId, newCommentDto);
        return commentService.moderateCommentByAdmin(commentId, newCommentDto);
    }

    @DeleteMapping("/{commentId}")
    public void deleteCommentAdmin(@PathVariable(value = "commentId") @Positive Long commentId) {
        log.info("Delete comment by admin: commentId: {}", commentId);
        commentService.deleteCommentAdmin(commentId);
    }

    @PatchMapping("/users/{userId}/ban")
    public void banUser(
            @PathVariable(value = "userId") @Positive Long userId,
            @RequestParam(value = "timeToUnban", required = false,
                    defaultValue = "MAX") String timeToUnban) {
        log.info("Ban user by admin: userId {}, timeToUnban {}.", userId, timeToUnban);
        commentService.banUser(userId, timeToUnban);
    }

    @PatchMapping("/users/{userId}/unban")
    public void unbanUser(@PathVariable(value = "userId") @Positive Long userId) {
        log.info("Unban user by admin:: userId: {}", userId);
        commentService.unbanUser(userId);
    }
}
