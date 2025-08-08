package com.desafio.btg.pedidos.model

data class NovoPedidoDTO(
        val clienteId: String,
        val itens: List<String>
)
