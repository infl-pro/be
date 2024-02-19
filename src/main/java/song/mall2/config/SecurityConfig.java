package song.mall2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import song.mall2.domain.jwt.service.JwtService;
import song.mall2.security.handler.AccessDeniedHandlerImpl;
import song.mall2.security.handler.AuthenticationEntryPointImpl;
import song.mall2.security.handler.LoginFailureHandler;
import song.mall2.security.handler.LoginSuccessHandler;
import song.mall2.security.authentication.userprincipal.service.UserDetailsServiceImpl;
import song.mall2.security.filter.JwtFilter;
import song.mall2.security.filter.LogFilter;
import song.mall2.security.filter.UsernamePasswordFilter;

import java.util.Arrays;

import static org.springframework.security.web.util.matcher.RegexRequestMatcher.regexMatcher;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration configuration) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors
                        .configurationSource(corsConfigurationSource()))
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(request -> CorsUtils.isPreFlightRequest(request)).permitAll()
                        .requestMatchers("/refreshToken/**").permitAll()
                        .requestMatchers("/productList/**").permitAll()
                        .requestMatchers("/account/**").permitAll()
                        .requestMatchers("/file/**").permitAll()
                        .requestMatchers(regexMatcher("^/product/[0-9]+$")).permitAll()
                        .requestMatchers("/orders/carttest").permitAll()
                        .requestMatchers("/cart/**").authenticated()
                        .requestMatchers("/product/**").authenticated()
                        .requestMatchers("/payment").authenticated()
                        .requestMatchers("/orders/**").authenticated()
                        .requestMatchers("/orderProduct/**").authenticated()
                        .requestMatchers("/user/**").authenticated()
                        .anyRequest().permitAll())
                .formLogin(login -> login.disable())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(new AuthenticationEntryPointImpl())
                        .accessDeniedHandler(new AccessDeniedHandlerImpl()))
                .logout(logout -> logout.disable())
                .addFilterBefore(new UsernamePasswordFilter(authenticationManager(configuration), authenticationSuccessHandler(), authenticationFailureHandler()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new LogFilter(), JwtFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new LoginSuccessHandler(jwtService, objectMapper);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new LoginFailureHandler();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://52.79.222.161:8080",
                "http://localhost:3000", "http://52.79.222.161:3000",
                "https://localhost:443", "https://52.79.222.161:443",
                "https://shapp.shop", "https://shapp.shop:443",
                "http://localhost"));
        config.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(), HttpMethod.POST.name(),
                HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name(),
                HttpMethod.PATCH.name(), HttpMethod.PUT.name()));
        config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization", "X-Requested-With"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
