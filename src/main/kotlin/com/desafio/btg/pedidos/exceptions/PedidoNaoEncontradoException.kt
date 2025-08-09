package com.desafio.btg.pedidos.exceptions

import java.util.UUID

class PedidoNaoEncontradoException(id: UUID): RuntimeException("Pedido com ID: $id não encontrado") {
}