package br.com.chronos.server.queue.rabbitmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import br.com.chronos.server.queue.rabbitmq.exchanges.AuthExchange;
import br.com.chronos.server.queue.rabbitmq.exchanges.CollaborationExchange;
import br.com.chronos.server.queue.rabbitmq.exchanges.HourBankExchange;
import br.com.chronos.server.queue.rabbitmq.exchanges.WorkScheduleExchange;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

@Configuration
@Import({
    AuthExchange.class,
    CollaborationExchange.class,
    HourBankExchange.class,
    WorkScheduleExchange.class })
public class RabbitMqConfiguration {
  @Bean
  Jackson2JsonMessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
