package br.edu.ifpb.academico.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
           http.authorizeHttpRequests(auth -> auth
               .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
               .requestMatchers("/login").permitAll()
               .requestMatchers("/paginaPrincipal").permitAll()
               .requestMatchers("/emprestimo/**").hasAnyRole("ALUNO", "ADMIN")
               .requestMatchers("/carteirinha/**").hasAnyRole("ADMIN")
               .requestMatchers("/aluno/**").hasAnyRole("ADMIN")
               .requestMatchers("/usuario/**").hasAnyRole("ADMIN")
               .requestMatchers("/role/**").hasRole("ADMIN")


               .anyRequest().access(new WebExpressionAuthorizationManager("isAuthenticated() and !hasRole('ALUNO')"))
           ).formLogin(
       		form -> form
               .loginPage("/login")
               .defaultSuccessUrl("/paginaPrincipal", true)
               .permitAll()
           ).logout(logout -> logout
                   .logoutUrl("/logout")
                   .logoutSuccessUrl("/login?logout")
                   .permitAll()
           );

        return http.build();
    }

    @Bean
   public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
   }

}
