package ru.cft.shift.crowdfundingplatformapi.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class JwtAuthentication extends AbstractAuthenticationToken {

    public JwtAuthentication(TokenData tokenData) {
        super(List.of(new SimpleGrantedAuthority(tokenData.getRole().toString())));
        setDetails(tokenData);
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return super.getDetails();
    }

}
