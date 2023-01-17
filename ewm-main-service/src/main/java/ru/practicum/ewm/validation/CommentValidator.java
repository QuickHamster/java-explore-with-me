package ru.practicum.ewm.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.comment.model.CommentStatus;
import ru.practicum.ewm.comment.repo.CommentRepository;
import ru.practicum.ewm.exception.ForbiddenException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.util.Const;

@Component
@RequiredArgsConstructor
public class CommentValidator {

    private final CommentRepository commentRepository;

    public Comment validateCommentOrThrow(long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Comment id = %x not found.", id), "Model not found."));
    }

    public void validateUserIsNotCommentBanOrThrow(User user) {
        if (user.isCommentsBan()) {
            throw new ForbiddenException("User " + user.getId() + " can't leave comments until " +
                    user.getBanCommentsPeriod().format(Const.DATE_TIME_FORMATTER) + ".",
                    "Ban period has not expired.");
        }
    }

    public void validateUserIsOwnerCommentOrThrow(Long userId, Comment comment) {
        if (!comment.getCommentator().getId().equals(userId)) {
            throw new ForbiddenException("User " + userId + " isn't owner of the comment " +
                    comment.getId() + ".", "Unsupported operation.");
        }
    }

    public void validateCommentIsNotEditAfterAdminOrThrow(Comment comment) {
        if (comment.getStatus().equals(CommentStatus.ADMIN_UPDATED)) {
            throw new ForbiddenException("Comment " + comment.getId() + " is forbidden to edit after moderation by " +
                    "the administrator.", "Invalid operation.");
        }
    }

    public void validateUserIsNotAlreadyUnbanOrThrow(User user) {
        if (!user.isCommentsBan()) {
            throw new ForbiddenException("User " + user.getId() + " is not banned: " + user.isCommentsBan() +
                    "(" + user.getBanCommentsPeriod().format(Const.DATE_TIME_FORMATTER) + ").",
                    "Operation is not required.");
        }
    }
}
