package com.hwalaon.wezxro_server

import io.github.cdimascio.dotenv.Dotenv
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@SpringBootApplication
@EnableRedisRepositories(basePackages = [
	"com.hwalaon.wezxro_server.domain.*.persistence.redis",
	"com.hwalaon.wezxro_server.global.common.refreshToken.persistence.repository"
])
class WezxroServerApplication

fun main(args: Array<String>) {
	val dotenv = Dotenv.configure().load()

	dotenv.entries().forEach { entry ->
		System.setProperty(entry.key, entry.value)
	}

	runApplication<WezxroServerApplication>(*args)
}
