package com.springk.blog.configurations;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class SpringAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable("nk").filter(s->!s.isEmpty());
    }
}
