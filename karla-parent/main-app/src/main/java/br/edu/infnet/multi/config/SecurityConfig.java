package br.edu.infnet.multi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/h2-console/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "api/ocorrencias" ).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "api/ocorrencias/{id}" ).hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "api/ocorrencias/{id}/status" ).hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "api/ocorrencias/**" ).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "api/ocorrencias/{id}" ).hasAnyRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "api/ordem-servico" ).hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "api/ordem-servico/{id}" ).hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "api/ordem-servico/{id}/**" ).hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "api/ordem-servico/**" ).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "api/ordem-servico/{id}" ).hasAnyRole("ADMIN")

                        .anyRequest().authenticated())
                .httpBasic(withDefaults());

        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("adminPass"))
                .roles("ADMIN").build();
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("userPass"))
                .roles("USER").build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
