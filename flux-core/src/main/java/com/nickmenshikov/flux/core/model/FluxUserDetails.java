package com.nickmenshikov.flux.core.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class FluxUserDetails extends User {

    private final Long userId;

    public FluxUserDetails(Long userId, String username, Collection<? extends GrantedAuthority> authorities) {
        super(username, "", authorities);
        this.userId = userId;
    }

}
