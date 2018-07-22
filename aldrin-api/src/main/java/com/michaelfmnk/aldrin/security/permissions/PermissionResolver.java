package com.michaelfmnk.aldrin.security.permissions;

import com.michaelfmnk.aldrin.security.UserAuthentication;

import java.io.Serializable;

public interface PermissionResolver {
    String getTargetType();

    boolean hasPrivilege(UserAuthentication authentication, Serializable target);
}
