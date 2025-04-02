package com.kuponburada.KuponBurada.config;

import com.kuponburada.KuponBurada.security.JwtAuthenticationEntryPoint;
import com.kuponburada.KuponBurada.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, UserDetailsService userDetailsService, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(authorize -> authorize
                        // Auth - Public Endpoints
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/public/**").permitAll()

                        // Swagger/OpenAPI - Public Endpoints
                        .requestMatchers("/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()

                        // Brand - Public Endpoints
                        .requestMatchers(HttpMethod.GET, "/api/brands").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/brands/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/brands/popular").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/brands/related-brands/**").permitAll()

                        // Brand - Private Endpoints
                        .requestMatchers(HttpMethod.POST, "/api/brands").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/brands/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/brands/{id}").authenticated()
                        .requestMatchers("/api/brands/follow-brand/**").authenticated()
                        .requestMatchers("/api/brands/unfollow-brand/**").authenticated()
                        .requestMatchers("/api/brands/followed-brands").authenticated()

                        // Category - Public Endpoints
                        .requestMatchers(HttpMethod.GET, "/api/categories").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categories/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categories/{id}/brands").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categories/{id}/coupons").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categories/popular").permitAll()

                        // Category - Private Endpoints
                        .requestMatchers(HttpMethod.POST, "/api/categories").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/categories/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/categories/**").authenticated()

                        // Coupon - Public Endpoints
                        .requestMatchers(HttpMethod.GET, "/api/coupons").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/coupons/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/coupons/last-added").permitAll()

                        // Coupon - Private Endpoints
                        .requestMatchers(HttpMethod.POST, "/api/coupons").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/coupons/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/coupons/**").authenticated()

                        // Notification - Private Endpoints
                        .requestMatchers("/api/notifications/**").authenticated()

                        // User - Private Endpoints
                        .requestMatchers("/api/user/**").authenticated()

                        // Default Rule
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}