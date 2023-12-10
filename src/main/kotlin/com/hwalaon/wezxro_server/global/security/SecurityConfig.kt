package com.hwalaon.wezxro_server.global.security

import com.hwalaon.wezxro_server.global.security.filter.ExceptionFilter
import com.hwalaon.wezxro_server.global.security.handler.CustomAccessDeniedHandler
import com.hwalaon.wezxro_server.global.security.handler.CustomAuthenticationEntryPoint
import com.hwalaon.wezxro_server.global.security.jwt.JwtAuthFilter
import com.hwalaon.wezxro_server.global.security.jwt.JwtParser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
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
    private val jwtParser: JwtParser
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.httpBasic { it.disable() }
            .cors { it.disable() }
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(RequestMatcher { req ->
                    CorsUtils.isPreFlightRequest(req)
                }).permitAll()

                it.anyRequest().permitAll()
            }
            .formLogin { it.disable() }
            .exceptionHandling {
                it.authenticationEntryPoint(CustomAuthenticationEntryPoint())
                    .accessDeniedHandler(CustomAccessDeniedHandler())
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(JwtAuthFilter(jwtParser), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(ExceptionFilter(), JwtAuthFilter::class.java)

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}