package com.desafio.btg.pedidos.model

import java.util.*

data class Pedido(
        val id: UUID,
        val clienteId: String,
        val itens: List<String>,
        var status: StatusPedido = StatusPedido.PENDENTE
)
