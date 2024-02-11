package com.ibardos.motoShop.security.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * Service class of CustomUserDetails type.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    // Injected dependencies
    private final ApplicationUserRepository applicationUserRepository;


    /**
     * Gets an ApplicationUser object from DB by username, and maps it into a CustomUserDetails object.
     * @param username identifying the user whose data is required from DB.
     * @return CustomUserDetails object with data necessary for authentication purposes.
     */
    @Override
    public CustomUserDetails loadUserByUsername(String username) {
        Optional<ApplicationUser> userFromDb = applicationUserRepository.findByUsername(username);
        return new CustomUserDetails(userFromDb.orElseThrow(() -> new UsernameNotFoundException("ApplicationUser with username: " + username + "not found.")));
    }
}
