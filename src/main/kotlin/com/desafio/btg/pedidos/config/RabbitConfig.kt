package com.desafio.btg.pedidos.config

import org.springframework.amqp.core.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfig {

    companion object {
        const val QUEUE_NAME = "fila-pedidos"
        const val EXCHANGE_NAME = "pedidos-exchange"
        const val ROUNTIG_KEY = "pedidos.novos"
    }

    @Bean
    fun queue(): Queue = Queue(QUEUE_NAME, false)

    @Bean
    fun exchange(): TopicExchange = TopicExchange(EXCHANGE_NAME)

    @Bean
    fun binding(queue: Queue, exchange: TopicExchange): Binding =
            BindingBuilder.bind(queue).to(exchange).with(ROUNTIG_KEY)
}