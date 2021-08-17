package com.ucv.codetech.facade;

import com.ucv.codetech.StartupComponent.Facade;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Facade
public class AuthenticationFacade {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
