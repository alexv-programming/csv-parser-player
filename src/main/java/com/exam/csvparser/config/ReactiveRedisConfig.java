package com.exam.csvparser.config;

import com.exam.csvparser.model.Player;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class ReactiveRedisConfig {

    @Bean
    public ReactiveRedisTemplate<String, Player> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Player> jsonSerializer = new Jackson2JsonRedisSerializer<>(Player.class);

        RedisSerializationContext<String, Player> serializationContext = RedisSerializationContext
                .<String, Player>newSerializationContext(jsonSerializer)
                .key(RedisSerializer.string())
                .value(jsonSerializer)
                .hashKey(RedisSerializer.string())
                .hashValue(jsonSerializer)
                .build();

        return new ReactiveRedisTemplate<>(factory, serializationContext);
    }
}
