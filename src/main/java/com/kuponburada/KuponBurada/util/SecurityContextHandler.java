package com.kuponburada.KuponBurada.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;

import com.kuponburada.KuponBurada.entity.User; // Burada kendi User entity'nizi import edin
import com.kuponburada.KuponBurada.repository.UserRepository; // User repository'nizi import edin
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityContextHandler {

    private final UserRepository userRepository;

    /**
     * Gets the ID of the currently authenticated user
     * 
     * @return The user ID of the authenticated user, or null if not found
     */
    public Long getCurrentUserId() {
        String username = getCurrentUsername();
        
        if (username == null) {
            return null;
        }
        
        User user = userRepository.findByUsername(username)
            .orElse(null);
            
        return user != null ? user.getId() : null;
    }
    
    /**
     * Gets the username of the currently authenticated user
     * 
     * @return The username of the authenticated user
     */
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        
        Object principal = authentication.getPrincipal();
        
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
    
    /**
     * Checks if a user is currently authenticated
     * 
     * @return true if a user is authenticated, false otherwise
     */
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }
}