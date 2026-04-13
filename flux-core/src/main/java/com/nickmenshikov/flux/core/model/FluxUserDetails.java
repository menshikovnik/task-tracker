package com.nickmenshikov.flux.core.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class FluxUserDetails extends User {

    private final com.nickmenshikov.flux.core.model.User user;

    public FluxUserDetails(com.nickmenshikov.flux.core.model.User user, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.user = user;
    }

}
