package ru.practicum.ewm.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/users/{userId}/comments")
@Validated
public class CommentControllerPrivate {

    private final CommentService commentService;

    @PostMapping("/events/{eventId}")
    public CommentDto addComment(@PathVariable(value = "userId") @Positive Long userId,
                                 @PathVariable(value = "eventId") @Positive Long eventId,
                                 @RequestBody @Valid NewCommentDto newCommentDto) {
        log.info("Add comment: userId {}, eventId {}, newCommentDto {}.", userId, eventId, newCommentDto);
        return commentService.addComment(userId, eventId, newCommentDto);
    }

    @PatchMapping("/{commentId}")
    public CommentDto updateComment(@PathVariable(value = "userId") @Positive Long userId,
                                    @PathVariable(value = "commentId") @Positive Long commentId,
                                    @RequestBody @Valid NewCommentDto newCommentDto) {
        log.info("Update comment: userId {}, commentId {}, newCommentDto {}.", userId, commentId, newCommentDto);
        return commentService.updateComment(userId, commentId, newCommentDto);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable(value = "userId") @Positive Long userId,
                              @PathVariable(value = "commentId") @Positive Long commentId) {
        log.info("Delete comment: userId {}, commentId {}.", userId, commentId);
        commentService.deleteComment(userId, commentId);
    }

    @GetMapping()
    public List<CommentDto> getAllCommentsByOwnerId(@PathVariable(value = "userId") @Positive Long userId) {
        log.info("Get all comments: userId {}.", userId);
        return commentService.getAllCommentsByOwnerId(userId);
    }

    @GetMapping("/{commentId}")
    public CommentDto getCommentByOwnerAndCommentIds(@PathVariable(value = "userId") @Positive Long userId,
                                                     @PathVariable(value = "commentId") @Positive Long commentId) {
        log.info("Get comment: userId {}, commentId {}.", userId, commentId);
        return commentService.getCommentByOwnerAndCommentIds(userId, commentId);
    }

    @GetMapping("/events/{eventId}")
    public List<CommentDto> getAllCommentsByOwnerAndEventIds(@PathVariable(value = "userId") @Positive Long userId,
                                                             @PathVariable(value = "eventId") @Positive Long eventId) {
        log.info("Get all comments: userId {}, eventId {}.", userId, eventId);
        return commentService.getAllCommentsByOwnerAndEventIds(userId, eventId);
    }


}
