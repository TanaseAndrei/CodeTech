package com.ucv.codetech.facade;

import com.ucv.codetech.StartupComponent.Facade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Facade
@Slf4j
public class AuthenticationFacade {

    public Authentication getAuthentication() {
        log.info("Retrieving current logged user");
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
