package com.hwalaon.wezxro_server

import io.github.cdimascio.dotenv.Dotenv
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Profile
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@SpringBootApplication
@EnableRedisRepositories(basePackages = [
	"com.hwalaon.wezxro_server.domain.*.persistence.redis",
	"com.hwalaon.wezxro_server.global.common.refreshToken.persistence.repository"
])
class WezxroServerApplication

@Profile("local")
fun initDotenv() {
	val dotenv = Dotenv.configure().load()

	dotenv.entries().forEach { entry ->
		System.setProperty(entry.key, entry.value)
	}
}

fun main(args: Array<String>) {
	// local 프로필이면 .env 파일을 읽어옴
	if ((System.getProperty("spring.profiles.active") ?: "default") == "local") {
		initDotenv()
	}

	runApplication<WezxroServerApplication>(*args)
}
