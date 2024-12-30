package id.my.hendisantika.backend.global.RsData.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * Created by IntelliJ IDEA.
 * Project : backend
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 31/12/24
 * Time: 06.07
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers("/api/*/articles").permitAll()
                                .requestMatchers("/api/*/articles/*").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/*/members/login").permitAll() // Anyone can log in, only post requests are allowed
                                .requestMatchers(HttpMethod.POST, "/api/*/members/logout").permitAll() // Anyone can log out
                                .anyRequest().authenticated()
                )
                .csrf(
                        csrf -> csrf
                                .disable()
                ) // turn off csrf token
                .httpBasic(
                        httpBasic -> httpBasic.disable()
                ) // Disable httpBasic login method
                .formLogin(
                        formLogin -> formLogin.disable()
                ) // Disable form login method
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(STATELESS)
                ) // Turn off session
                .addFilterBefore(
                        jwtAuthorizationFilter, // Login processing using access token
                        UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }
}
