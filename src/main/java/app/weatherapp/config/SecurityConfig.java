//package app.weatherapp.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//
//@RequiredArgsConstructor
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    private final CorsConfig corsConfig;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecur) throws Exception{
//        httpSecur
//                .cors()
//                .configurationSource(corsConfig.corsConfigurationSource())
//                .and()
//                .formLogin().disable()
//                .csrf().disable()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeHttpRequests()
//                .requestMatchers("/**").permitAll()
//                .anyRequest().authenticated();
//
//        return httpSecur.build();
//    }
//}
