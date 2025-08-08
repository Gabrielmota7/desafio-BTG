package com.desafio.btg.pedidos.service

import com.desafio.btg.pedidos.messaging.PedidoProducer
import com.desafio.btg.pedidos.model.NovoPedidoDTO
import com.desafio.btg.pedidos.model.Pedido
import com.desafio.btg.pedidos.repository.PedidoRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PedidoService (
        private val pedidoRepository: PedidoRepository,
        private val pedidoProducer: PedidoProducer
) {

    fun criarPedido(dto: NovoPedidoDTO): Pedido {
        val pedido= Pedido(
                id = UUID.randomUUID(),
                clienteId = dto.clienteId,
                itens = dto.itens
                )
        pedidoRepository.salvar(pedido)
        pedidoProducer.enviarPedidoParaFila(pedido.id)
        return pedido
    }

    fun buscarPedido(id: UUID): Pedido? {
        return pedidoRepository.buscarPorId(id)
    }

    fun marcarProcessado(id: UUID) {
        pedidoRepository.atualizarStatus(id)
    }

}