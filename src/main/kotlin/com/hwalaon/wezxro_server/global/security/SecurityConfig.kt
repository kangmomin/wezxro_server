package com.hwalaon.wezxro_server.global.security

import com.hwalaon.wezxro_server.domain.account.model.constant.AccountRole
import com.hwalaon.wezxro_server.global.security.filter.ExceptionFilter
import com.hwalaon.wezxro_server.global.security.handler.CustomAccessDeniedHandler
import com.hwalaon.wezxro_server.global.security.handler.CustomAuthenticationEntryPoint
import com.hwalaon.wezxro_server.global.security.jwt.JwtAuthFilter
import com.hwalaon.wezxro_server.global.security.jwt.JwtParser
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.core.GrantedAuthorityDefaults
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.web.cors.CorsUtils

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtParser: JwtParser,
    private val principalDetailsService: PrincipalDetailsService,
    private val exceptionFilter: ExceptionFilter
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.httpBasic { it.disable() }
            .cors {}
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(RequestMatcher { req ->
                    CorsUtils.isPreFlightRequest(req)
                }).permitAll()

                it.requestMatchers("/u/login", "/u/join", "/u/login/demo", "/master/c/*", "/d/check", "/api/v2").permitAll()
                    .requestMatchers("/admin/**").hasRole(AccountRole.ADMIN.toString())
                    .requestMatchers(HttpMethod.GET, "/**").permitAll()
                    // GET을 제외한 모든 요청에 대해 "USER", "ADMIN", "MASTER" 역할 요구
                    .requestMatchers(HttpMethod.POST, "/**").hasAnyRole("USER", "ADMIN", "MASTER")
                    .requestMatchers(HttpMethod.PATCH, "/**").hasAnyRole("USER", "ADMIN", "MASTER")
                    .requestMatchers(HttpMethod.DELETE, "/**").hasAnyRole("USER", "ADMIN", "MASTER")
                    .anyRequest().authenticated()
            }
            .formLogin { it.disable() }
            .exceptionHandling {
                it.authenticationEntryPoint(CustomAuthenticationEntryPoint())
                    .accessDeniedHandler(CustomAccessDeniedHandler())
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(JwtAuthFilter(jwtParser, principalDetailsService), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(exceptionFilter, JwtAuthFilter::class.java)

        return http.build()
    }

    @Bean
    fun grantedAuthorityDefaults(): GrantedAuthorityDefaults {
        return GrantedAuthorityDefaults("") // Remove the ROLE_ prefix
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}