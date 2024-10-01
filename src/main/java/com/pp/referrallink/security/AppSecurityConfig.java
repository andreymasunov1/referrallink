package com.pp.referrallink.security;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

  private final UserDetailsService userDetailsService;
  private final AuthenticationSuccessHandler successHandler;

  public AppSecurityConfig(@Autowired UserDetailsService UserDetailsService,
      @Autowired AuthenticationSuccessHandler successHandler) {
    this.userDetailsService =  UserDetailsService;
    this.successHandler = successHandler;
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.authorizeHttpRequests(auth -> auth.requestMatchers("/",
                "/login",
                "/register",
                "/search**",
                "/index",
                "/oauth2/**",
                "/getRandomLink/**")
            .permitAll()
            .requestMatchers("/css/**", "/js/**", "/icons/**", "/logos/**", "/static/**")
            .permitAll()
            .anyRequest().authenticated())
            .formLogin(auth -> auth.loginPage("/login").defaultSuccessUrl("/", true).successHandler(successHandler))
            .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true).deleteCookies("JSESSIONID"))
        .oauth2Login(auth -> auth.loginPage("/login").defaultSuccessUrl("/", true).successHandler(successHandler));


//    http.authorizeHttpRequests(auth -> auth.requestMatchers("/",
//            "/login",
//            "/register",
//            "/search**",
//            "/index",
//            "/oauth2/**",
//            "/getRandomLink/**")
//        .permitAll()
//        .requestMatchers("/css/**", "/js/**", "/images/**")
//        .permitAll()
//        .anyRequest().authenticated()).oauth2Login(oauth2Login -> oauth2Login
//        .userInfoEndpoint(userInfo -> userInfo
//            .userAuthoritiesMapper(grantedAuthoritiesMapper())
//        )
//    );
//
//    http.formLogin(auth -> auth.loginPage("/login").defaultSuccessUrl("/", true))
//        .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true).deleteCookies("JSESSIONID"));

    return http.build();
  }

  private GrantedAuthoritiesMapper grantedAuthoritiesMapper() {
    return (authorities) -> {
      Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

      authorities.forEach((authority) -> {
        GrantedAuthority mappedAuthority;

        if (authority instanceof OidcUserAuthority) {
          OidcUserAuthority userAuthority = (OidcUserAuthority) authority;
          mappedAuthority = new OidcUserAuthority(
              "OIDC_USER", userAuthority.getIdToken(), userAuthority.getUserInfo());
        } else if (authority instanceof OAuth2UserAuthority) {
          OAuth2UserAuthority userAuthority = (OAuth2UserAuthority) authority;
          mappedAuthority = new OAuth2UserAuthority(
              "OAUTH2_USER", userAuthority.getAttributes());
        } else {
          mappedAuthority = authority;
        }

        mappedAuthorities.add(mappedAuthority);
      });

      return mappedAuthorities;
    };
  }
}
