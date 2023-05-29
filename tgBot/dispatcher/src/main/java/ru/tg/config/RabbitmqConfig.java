package ru.tg.config;

import org.springframework.amqp.support.converter.Jackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    public MessageConverter jsonMessageConverter {
        return new Jackson2JsonMessageConverter();
    }
}
