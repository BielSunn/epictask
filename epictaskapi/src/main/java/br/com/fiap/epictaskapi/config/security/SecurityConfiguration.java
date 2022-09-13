package br.com.fiap.epictaskapi.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.httpBasic()
            .and()
            .authorizeHttpRequests()
            .antMatchers(HttpMethod.GET, "/api/task/**").permitAll()
            .antMatchers(HttpMethod.POST, "/api/task/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/user/**").permitAll()
            .antMatchers(HttpMethod.POST, "/api/user/**").permitAll()
            .antMatchers(HttpMethod.DELETE, "/api/user/**").authenticated()
            .antMatchers(HttpMethod.PUT, "/api/user/**").authenticated()
                .anyRequest().denyAll()
            .and()
                .csrf().disable()
            ;
        return http.build();
    }

    // @Bean
    // public UserDetailsService users(){
    //     UserDetails user = User.builder()
    //         .username("joao@fiap.com.br")
    //         .password("$2a$12$fJRy5k93hXvZbgFvXS0eN.FjZ5ykqjYHIhuNDTr62C/Pj10y6eorG")
    //         .roles("USER")
    //         .build();

    //     return new InMemoryUserDetailsManager(user);
    // }

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }
    
}
