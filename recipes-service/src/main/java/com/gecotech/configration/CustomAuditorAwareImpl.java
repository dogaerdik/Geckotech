package com.gecotech.configration;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class CustomAuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {

        // Can use Spring Security to return currently logged in user
        return Optional.of("D.Erdik");
    }

}