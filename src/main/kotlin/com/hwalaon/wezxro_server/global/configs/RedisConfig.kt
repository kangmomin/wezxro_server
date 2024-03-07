package com.hwalaon.wezxro_server.global.configs

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories


@Configuration
@EnableRedisRepositories
class RedisConfig(
    @Value("\${spring.data.redis.host}")
    private val host: String,
    @Value("\${spring.data.redis.port}")
    private val port: Int
) {

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory = LettuceConnectionFactory(host, port)

    @Bean
    fun redisTemplate(): RedisTemplate<*, *> {
        val template = RedisTemplate<ByteArray, ByteArray>()
        template.connectionFactory = redisConnectionFactory()
        return template
    }
}