package io.github.tobi.laa.spring.boot.embedded.redis.server

import io.github.tobi.laa.spring.boot.embedded.redis.EmbeddedRedisSpringExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ContextCustomizerFactories
import kotlin.reflect.KClass

/**
 * Annotation to enable a singular [embedded Redis server][redis.embedded.RedisServer] for tests.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@ExtendWith(EmbeddedRedisSpringExtension::class)
@ContextCustomizerFactories(RedisServerContextCustomizerFactory::class)
annotation class EmbeddedRedisServer(
    /**
     * The port to start the embedded Redis server on. If set to 0, a free port upwards from `6379` will be used.
     * @see redis.embedded.core.RedisServerBuilder.port
     */
    val port: Int = 0,

    /**
     * The path to the Redis config file to use. If set, the config will be loaded from the file.
     * @see redis.embedded.core.RedisServerBuilder.configFile
     */
    val configFile: String = "",

    /**
     * The bind address to use. If set, the server will only bind to the given address.
     * @see redis.embedded.core.RedisServerBuilder.bind
     */
    val bind: String = "localhost",

    /**
     * The setting to use. If set, the server will be started with the given settings.
     * @see redis.embedded.core.RedisServerBuilder.setting
     */
    val settings: Array<String> = [],

    /**
     * The path to the directory to execute the Redis server in. If set, the Redis executable will be executed in the
     * given directory.
     * @see redis.embedded.core.ExecutableProvider.newJarResourceProvider
     */
    val executeInDirectory: String = "",

    /**
     * Customizes how the Redis server is built. Customizers are ordered by their natural order in this array. Each
     * customizer must have no-arg constructor.
     * @see RedisServerCustomizer
     */
    val customizer: Array<KClass<out RedisServerCustomizer>> = []
)