package ru.practicum.ewm.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.comment.model.CommentStatus;
import ru.practicum.ewm.comment.model.dto.CommentDto;
import ru.practicum.ewm.comment.model.dto.CommentMapper;
import ru.practicum.ewm.comment.model.dto.NewCommentDto;
import ru.practicum.ewm.comment.repo.CommentRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repo.UserRepository;
import ru.practicum.ewm.util.Const;
import ru.practicum.ewm.validation.CommentValidator;
import ru.practicum.ewm.validation.DateValidator;
import ru.practicum.ewm.validation.EventValidator;
import ru.practicum.ewm.validation.UserValidator;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserValidator userValidator;
    private final EventValidator eventValidator;
    private final CommentValidator commentValidator;
    private final CommentRepository commentRepository;
    private final DateValidator dateValidator;
    private final UserRepository userRepository;

    @Override
    public CommentDto addComment(Long userId, Long eventId, NewCommentDto newCommentDto) {
        log.info("Trying add comment: userId {}, eventId {} , newCommentDto {}.", userId, eventId, newCommentDto);
        User user = userValidator.validateUserOrThrow(userId);
        commentValidator.validateUserIsNotCommentBanOrThrow(user);
        Event event = eventValidator.validateEventOrThrow(eventId);
        eventValidator.validateEventStatusNotPublishedOrThrow(event);
        Comment comment = CommentMapper.fromNewCommentDtoToComment(newCommentDto, event, user);
        CommentDto commentDto = CommentMapper.toCommentDto(commentRepository.save(comment));
        log.info("Comment add successfully: {}.", commentDto);
        return commentDto;
    }

    @Override
    public CommentDto updateComment(Long userId, Long commentId, NewCommentDto newCommentDto) {
        log.info("Trying update comment: userId {}, commentId {}, newCommentDto {}.", userId, commentId, newCommentDto);
        User user = userValidator.validateUserOrThrow(userId);
        commentValidator.validateUserIsNotCommentBanOrThrow(user);
        Comment comment = commentValidator.validateCommentOrThrow(commentId);
        commentValidator.validateUserIsOwnerCommentOrThrow(userId, comment);
        commentValidator.validateCommentIsNotEditAfterAdminOrThrow(comment);
        comment.setText(newCommentDto.getText());
        comment.setUpdatedOn(LocalDateTime.now());
        comment.setStatus(CommentStatus.USER_UPDATED);
        CommentDto commentDto = CommentMapper.toCommentDto(commentRepository.save(comment));
        log.info("Comment update successfully: {}.", commentDto);
        return commentDto;

    }

    @Override
    public void deleteComment(Long userId, Long commentId) {
        log.info("Trying delete comment: userId: {}, commentId: {}", userId, commentId);
        Comment comment = commentValidator.validateCommentOrThrow(commentId);
        commentValidator.validateUserIsOwnerCommentOrThrow(userId, comment);
        commentRepository.delete(comment);
        log.info("Comment delete successfully: {}.", comment.getId());
    }

    @Override
    public CommentDto getCommentByOwnerAndCommentIds(Long userId, Long commentId) {
        log.info("Trying to get comment: userId {}, commentId {}.", userId, commentId);
        userValidator.validateUserOrThrow(userId);
        commentValidator.validateCommentOrThrow(commentId);
        Comment comment = commentRepository.findByCommentatorAndCommentIds(userId, commentId);
        CommentDto commentDto = CommentMapper.toCommentDto(comment);
        log.info("Get comment successfully: {}.", commentDto);
        return commentDto;
    }

    @Override
    public List<CommentDto> getAllCommentsByOwnerId(Long userId) {
        log.info("Trying to get all comments: userId {}.", userId);
        userValidator.validateUserOrThrow(userId);
        List<Comment> comments = commentRepository.findAllByCommentatorId(userId);
        List<CommentDto> commentDtoList = CommentMapper.toCommentDtoList(comments);
        log.info("Get all comments successfully: {}.", commentDtoList);
        return commentDtoList;
    }

    @Override
    public List<CommentDto> getAllCommentsByOwnerAndEventIds(Long userId, Long eventId) {
        log.info("Trying to get all comments: userId {}, eventId {}.", userId, eventId);
        userValidator.validateUserOrThrow(userId);
        eventValidator.validateEventOrThrow(eventId);
        List<Comment> comments = commentRepository.finAllByCommentatorAndEventIds(userId, eventId);
        List<CommentDto> commentDtoList = CommentMapper.toCommentDtoList(comments);
        log.info("Get all comments successfully: {}.", commentDtoList);
        return commentDtoList;
    }

    @Override
    public CommentDto moderateCommentByAdmin(Long commentId, NewCommentDto newCommentDto) {
        log.info("Trying to moderate a comment by an admin: commentId {}, newCommentDto {}.", commentId,
                newCommentDto);
        Comment comment = commentValidator.validateCommentOrThrow(commentId);
        comment.setText(newCommentDto.getText());
        comment.setStatus(CommentStatus.ADMIN_UPDATED);
        comment.setUpdatedOn(LocalDateTime.now());
        CommentDto commentDto = CommentMapper.toCommentDto(commentRepository.save(comment));
        log.info("Moderate a comment by an admin successfully: {}.", commentDto);
        return commentDto;
    }

    @Override
    public void deleteCommentAdmin(Long commentId) {
        log.info("Trying to delete comment by admin: commentId {}.", commentId);
        Comment comment = commentValidator.validateCommentOrThrow(commentId);
        commentRepository.delete(comment);
        log.info("Delete comment by admin successfully: {}.", comment.getId());
    }

    @Override
    public void banUser(Long userId, String timeToUnban) {
        log.info("Trying to ban user by admin: userId {}, timeToUnban {}.", userId, timeToUnban);
        User user = userValidator.validateUserOrThrow(userId);
        if (timeToUnban.equals("MAX")) {
            timeToUnban = LocalDateTime.now().plusYears(1000).format(Const.DATE_TIME_FORMATTER);
        }
        LocalDateTime ldtToUnban = dateValidator.validateFormatDateOrThrow(timeToUnban);
        dateValidator.validateDateAfterNowOrThrow(ldtToUnban);
        user.setBanCommentsPeriod(ldtToUnban);
        userRepository.save(user);
        log.info("Ban to set successfully to user {}.", user.getId());
    }

    @Override
    public void unbanUser(Long userId) {
        log.info("Trying to unban user by admin: userId {}.", userId);
        User user = userValidator.validateUserOrThrow(userId);
        user.setBanCommentsPeriod(LocalDateTime.now());
        user = userRepository.save(user);
        log.info("User {} unban successfully.", user.getId());
    }

    @Override
    public CommentDto getCommentAdmin(Long commentId) {
        log.info("Trying to get comment by admin: commentId {}.", commentId);
        Comment comment = commentValidator.validateCommentOrThrow(commentId);
        CommentDto commentDto = CommentMapper.toCommentDto(comment);
        log.info("Get comment by admin successfully: {}.", commentDto);
        return commentDto;
    }

    @Override
    public List<CommentDto> findCommentsAdminByEventId(Long eventId) {
        log.info("Trying to find all comments by admin: eventId {}.", eventId);
        eventValidator.validateEventOrThrow(eventId);
        List<Comment> comments = commentRepository.findAllByEventId(eventId);
        List<CommentDto> commentDtoList = CommentMapper.toCommentDtoList(comments);
        log.info("Find all comments by admin successfully: {}.", commentDtoList);
        return commentDtoList;
    }

    @Override
    public List<CommentDto> findCommentsAdminByCommentatorId(Long commentatorId) {
        log.info("Trying to find all comments by admin: commentatorId {}.", commentatorId);
        userValidator.validateUserOrThrow(commentatorId);
        List<Comment> comments = commentRepository.findAllByCommentatorId(commentatorId);
        List<CommentDto> commentDtoList = CommentMapper.toCommentDtoList(comments);
        log.info("Find all comments by admin successfully: {}.", commentDtoList);
        return commentDtoList;
    }

    @Override
    public List<CommentDto> findCommentsAdminByCommentatorAndEventIds(Long userId, Long eventId) {
        log.info("Trying to find all comments by admin: userId {}, eventId {}.", userId, eventId);
        userValidator.validateUserOrThrow(userId);
        eventValidator.validateEventOrThrow(eventId);
        List<Comment> comments = commentRepository.finAllByCommentatorAndEventIds(userId, eventId);
        List<CommentDto> commentDtoList = CommentMapper.toCommentDtoList(comments);
        log.info("Find all comments by admin successfully: {}.", commentDtoList);
        return commentDtoList;
    }
}
