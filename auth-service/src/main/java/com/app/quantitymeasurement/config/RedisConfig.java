package com.app.quantitymeasurement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis configuration for the Auth Service.
 *
 * <p>Provides a {@link StringRedisTemplate} bean used by {@code TokenBlacklistService}
 * to store blacklisted JWT IDs (JTIs) with automatic TTL expiry.</p>
 *
 * <p>The service uses {@code @Autowired(required = false)} on the template,
 * so if Redis is unavailable it gracefully falls back to in-memory storage.</p>
 */
@Configuration
public class RedisConfig {

    /**
     * Configures a StringRedisTemplate with String serializers for both keys and values.
     * Keys are stored as: {@code blacklist:jti:<jti-value>}
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}
