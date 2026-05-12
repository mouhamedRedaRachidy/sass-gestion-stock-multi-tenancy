package com.rachidy.sassgestionstockapp.auth.services.impl;

import com.rachidy.sassgestionstockapp.auth.repository.UserRepository;
import com.rachidy.sassgestionstockapp.auth.services.UserDetailService;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailService {
    private final UserRepository userRepository;

    @Override
    @Nonnull
    public UserDetails loadUserByUsername(@Nonnull  String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("user"));
    }
}
