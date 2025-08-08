package com.desafio.btg.pedidos.messaging

import com.desafio.btg.pedidos.config.RabbitConfig
import com.desafio.btg.pedidos.service.PedidoService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.util.*

@Component
class PedidoConsumer(
        private val pedidoService: PedidoService
) {

    @Async
    @RabbitListener(queues =  [RabbitConfig.QUEUE_NAME])
    fun consumirFila(pedidoId: String) {
        println("pedido recebido: $pedidoId")
        Thread.sleep(2000)
        pedidoService.marcarProcessado(UUID.fromString(pedidoId))
        println("Pedido Processado: $pedidoId")
    }
}