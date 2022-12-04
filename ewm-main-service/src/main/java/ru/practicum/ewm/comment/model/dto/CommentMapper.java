package ru.practicum.ewm.comment.model.dto;

import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.comment.model.CommentStatus;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.util.Const;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.user.model.dto.UserMapper.toUserDto;

public class CommentMapper {
    public static Comment fromNewCommentDtoToComment(NewCommentDto commentDto, Event event, User user) {
        return Comment.builder()
                .text(commentDto.getText())
                .commentator(user)
                .status(CommentStatus.ORIGINAL)
                .updatedOn(LocalDateTime.now())
                .event(event)
                .build();
    }

    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .createdOn(comment.getCreatedOn().format(Const.DATE_TIME_FORMATTER))
                .commentator(toUserDto(comment.getCommentator()))
                .status(comment.getStatus().name())
                .updatedOn(comment.getUpdatedOn() != null
                        ? comment.getUpdatedOn().format(Const.DATE_TIME_FORMATTER)
                        : "null")
                .event(toEventCommentShortDto(comment.getEvent()))
                .build();
    }

    public static EventCommentShortDto toEventCommentShortDto(Event event) {
        return EventCommentShortDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .build();
    }

    public static List<CommentDto> toCommentDtoList(List<Comment> comments) {
        return comments.stream().map(CommentMapper::toCommentDto).collect(Collectors.toList());
    }
}
