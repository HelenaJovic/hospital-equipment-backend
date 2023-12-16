package com.ISAproject.hospitalequipment.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
@Getter
@Setter
public class TokenBasedAuthentication extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 1L;

    private String token;
    private final UserDetails details;


    public TokenBasedAuthentication(UserDetails details) {
        super(details.getAuthorities());
        this.details = details;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return details;
    }
}
