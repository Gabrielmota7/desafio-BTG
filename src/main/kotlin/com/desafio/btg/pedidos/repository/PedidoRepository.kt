package com.desafio.btg.pedidos.repository

import com.desafio.btg.pedidos.model.Pedido
import com.desafio.btg.pedidos.model.StatusPedido
import org.springframework.stereotype.Repository
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Repository
class PedidoRepository {
    private val pedidos = ConcurrentHashMap<UUID, Pedido>()

    fun salvar(pedido: Pedido) {
        pedidos[pedido.id] = pedido
    }

    fun buscarPorId(id: UUID): Pedido? = pedidos[id]

    fun atualizarStatus(id: UUID){
        pedidos[id]?.status = StatusPedido.PROCESSADO
    }
}