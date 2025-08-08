package com.desafio.btg.pedidos.messaging

import com.desafio.btg.pedidos.config.RabbitConfig
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class PedidoProducer(
        private val rabbitTemplate: RabbitTemplate
) {

    fun enviarPedidoParaFila(pedidoId: UUID) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.ROUNTIG_KEY,
                pedidoId.toString()
        )
    }
}