package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import recipes.repos.UserDetailsServiceImp;
import recipes.repos.UsersRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(@Autowired UsersRepository userRepository) {
        return new UserDetailsServiceImp(userRepository);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(
                        authorize -> authorize
                                .mvcMatchers("/h2-console/**").permitAll()
                                .mvcMatchers("/actuator/shutdown").permitAll()
                                .mvcMatchers("/api/register").permitAll()
                                .mvcMatchers("/api/recipe/**").hasAnyAuthority(
                                        "ROLE_USER", "ROLE_ADMIN", "USER")
                                .anyRequest().authenticated()
                )
                .httpBasic();

        return http.build();
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

}

