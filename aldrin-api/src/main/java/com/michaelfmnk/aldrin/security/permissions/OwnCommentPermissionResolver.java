package com.michaelfmnk.aldrin.security.permissions;

import com.michaelfmnk.aldrin.entities.Comment;
import com.michaelfmnk.aldrin.security.UserAuthentication;
import com.michaelfmnk.aldrin.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;


@Component
@AllArgsConstructor
public class OwnCommentPermissionResolver implements PermissionResolver {

    public static final String OWN_COMMENT = "OWN_COMMENT";
    private final UserService userService;

    @Override
    public String getTargetType() {
        return OWN_COMMENT;
    }

    @Override
    public boolean hasPrivilege(UserAuthentication auth, Serializable target) {
        if (!(target instanceof Integer)) {
            return false;
        }
        return userService.findValidUserById(auth.getId())
                .getComments()
                .stream()
                .map(Comment::getCommentId)
                .anyMatch(id -> Objects.equals((Integer) target, id));
    }
}
