package io.github.tobi.laa.spring.boot.embedded.redis.server

import io.github.tobi.laa.spring.boot.embedded.redis.IntegrationTest
import io.github.tobi.laa.spring.boot.embedded.redis.RedisTests
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@IntegrationTest
@EmbeddedRedisServer(
    port = 10000,
    settings = [
        "appendonly no",
        "protected-mode yes",
        "appendfsync everysec",
        "port 12345"]
)
@DisplayName("Using @EmbeddedRedisServer with custom settings")
internal class CustomSettingsTest {

    @Autowired
    private lateinit var given: RedisTests

    @Test
    @DisplayName("It should be possible to write to Redis and the data should be available afterwards")
    @Order(2)
    fun givenRandomTestdata_writingToRedis_dataShouldBeAvailable() {
        given.randomTestdata()
            .whenRedis().isBeingWrittenTo()
            .then().redis().shouldContainTheTestdata()
    }

    @Test
    @DisplayName("Redis should have been flushed after the first test")
    @Order(3)
    fun redisShouldHaveBeenFlushed() {
        given.nothing()
            .whenDoingNothing()
            .then().redis().shouldNotContainAnyTestdata()
    }

    @Test
    @DisplayName("Settings from @EmbeddedRedisServer should have been applied to the embedded Redis server")
    @Order(4)
    fun configFileShouldHaveBeenApplied() {
        given.nothing()
            .whenDoingNothing()
            .then().embeddedRedis()
            .shouldHaveConfig().thatContainsDirective("appendonly", "no")
            .and().thatContainsDirective("protected-mode", "yes")
            .and().thatContainsDirective("appendfsync", "everysec")
    }
}