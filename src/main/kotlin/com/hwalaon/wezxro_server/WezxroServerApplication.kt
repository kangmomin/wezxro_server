package com.hwalaon.wezxro_server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@SpringBootApplication
@EnableRedisRepositories(basePackages = [
	"com.hwalaon.wezxro_server.domain.deposit.persistence.redis",
	"com.hwalaon.wezxro_server.global.common.refreshToken.persistence.repository"
])
class WezxroServerApplication

fun main(args: Array<String>) {
	runApplication<WezxroServerApplication>(*args)
}
