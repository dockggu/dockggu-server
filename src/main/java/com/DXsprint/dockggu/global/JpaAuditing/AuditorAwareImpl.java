package com.DXsprint.dockggu.global.JpaAuditing;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        System.out.println("AuditorAwareImpl.getCurrentAuditor");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")) {
            return Optional.empty();
        }
        String userId = authentication.getName();
        System.out.println("UserId : ====" + userId);
        return Optional.of(userId);
    }
}