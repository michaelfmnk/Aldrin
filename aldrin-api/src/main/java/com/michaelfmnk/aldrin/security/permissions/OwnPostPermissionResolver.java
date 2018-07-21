package com.michaelfmnk.aldrin.security.permissions;

import com.michaelfmnk.aldrin.entities.Post;
import com.michaelfmnk.aldrin.security.UserAuthentication;
import com.michaelfmnk.aldrin.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;


@Component
@AllArgsConstructor
public class OwnPostPermissionResolver implements PermissionResolver {

    public static final String OWN_POST = "OWN_POST";
    private final UserService userService;

    @Override
    public String getTargetType() {
        return OWN_POST;
    }

    @Override
    public boolean hasPrivilege(UserAuthentication auth, Serializable target) {
        if (!(target instanceof Integer)) {
            return false;
        }
        return userService.findValidUserById(auth.getId())
                .getPosts()
                .stream()
                .map(Post::getPostId)
                .anyMatch(postId -> Objects.equals((Integer) target, postId));
    }
}
